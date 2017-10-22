package com.tedu.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tedu.common.HttpContext;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;
import com.tedu.service.LoginServlet;
import com.tedu.service.RegServlet;
import com.tedu.service.UpdateServlet;

/**
 * 该线程任务用于处理每个客户端的请求
 * @author adminitartor
 *
 */
public class ClientHandler implements Runnable{
	
	//定义 uri 和 Servlet 类的对应关系
		private static Map<String, String> 
			uriMapping = new HashMap<String, String>();
		//初始化 uriMapping, 读取web.xml 填充到uriMapping
		static{
			try{
				File file= new File("webapp/web.xml");
				SAXReader reader = new SAXReader();
				Document doc = reader.read(file);
				//获取 webapp 根元素
				Element root = doc.getRootElement();
				//获取全部的 url-mapping 元素
				List<Element> list=
						root.elements("url-mapping");
				for (Element e : list) {
					//e 就是每个 url-mapping
					//e.elementText("url")读取e元素中
					//url子元素中的文本值
					String uri = e.elementText("url");
					String className=e.elementText("class");
					uriMapping.put(uri, className);
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	
	private Socket socket;
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try {
			InputStream in = socket.getInputStream();
			//创建对应的请求对象
			HttpRequest request = new HttpRequest(in);
			OutputStream out = socket.getOutputStream();
			//创建对应的响应对象
			HttpResponse response = new HttpResponse(out);
			
			/*
			 * 处理用户请求
			 */
			//获取用户请求资源路径
			/*
			 *  /index.html
			 *  /reg
			 */
			String uri = request.getUri();
			System.out.println("uri:"+uri);
			if("/".equals(uri)){
				//去首页
				File file = new File("webapp/index.html");
				responseFile(HttpContext.STATUS_CODE_OK, file, response);
			}else{
				File file = new File("webapp"+uri);
				if(file.exists()){
					System.out.println("找到了相应资源:"+file.length());
					responseFile(HttpContext.STATUS_CODE_OK,file,response);	
				}else{
					
					invoke(uri, request, response);
					
				}
				
				
				//查看是否请求一个功能
//				}else if("/reg".equals(uri)){
//					RegServlet servlet = new RegServlet();
//					servlet.service(request,response);
//					
//				}else if("/login".equals(uri)){
//					LoginServlet servlet = new LoginServlet();
//					servlet.service(request,response);
//					
//				}else if("/update".equals(uri)){
//					UpdateServlet servlet = new UpdateServlet();
//					servlet.service(request,response);
//					
//				}else{
//					System.out.println("没有资源:404");
//					file = new File("webapp/404.html");
//					responseFile(HttpContext.STATUS_CODE_NOTFOUND,file,response);	
//				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 根据给定的文件分析其名字后缀以获取
	 * 对应的ContenType
	 * @param file
	 * @return
	 */
	private String getContentTypeByFile(File file){
		//获取文件名
		String name = file.getName();
		
		//截取后缀
		String ext = name.substring(
				name.lastIndexOf(".")+1);
		
		//获取对应的ContentType
		String contentType 
			= HttpContext.contentTypeMapping.get(ext);
		return contentType;
				
	}
	/**
	 * 响应客户端指定资源
	 * @param status 响应状态码
	 * @param file 要响应的资源
	 * @throws Exception 
	 */
	private void responseFile(int status,File file,HttpResponse response) throws Exception{
		try {
			//1 设置状态行信息
			response.setStatus(status);
			//2 设置响应头信息
			//分析该文件后缀，根据后缀获取对应的ContentType
			response.setContentType(getContentTypeByFile(file));
			response.setContentLength((int)file.length());
			//3 设置响应正文
			response.setEntity(file);
			//4 响应客户端
			response.flush();	
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public void invoke(String uri, 
			HttpRequest request, HttpResponse response) 
		throws Exception{
		//根据uri 找到类名 
		//动态加载类到内存
		//动态创建对象
		//动态查找方法 service
		//动态调用方法
		String className = uriMapping.get(uri);
		Class cls=Class.forName(className);
		Object obj = cls.newInstance();
		Method method = cls.getDeclaredMethod(
				"service", request.getClass(),
				response.getClass());
		method.setAccessible(true); 
		method.invoke(obj, request,response); 
	}
	
	
}


