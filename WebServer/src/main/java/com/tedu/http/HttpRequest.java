package com.tedu.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.tedu.common.HttpContext;

/**
 * Http请求对象
 * 封装一个Http请求相关信息
 * @author adminitartor
 *
 */
public class HttpRequest {
	/*
	 * 请求行相关信息
	 */
	//请求方法
	private String method;
	//请求资源URI  (URI统一资源定位)
	private String uri;
	//请求协议版本
	private String protocol;
	
	//消息报头信息
	private Map<String,String> header;
	
	
	//存储客户端传递过来的参数
	private Map<String,String> parameters;
	
	
	public HttpRequest(InputStream in) throws Exception{
		try {
			System.out.println("http构造方法!");
			//1解析请求行
			parseRequestLine(in);
			//2解析消息头
			parseRequestHeader(in);
			//3解析消息正文(略)
			
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 解析请求行信息
	 * @param in
	 * @throws IOException 
	 */
	private void parseRequestLine(InputStream in) throws IOException{
		/*
		 * 实现步骤:
		 * 1:先读取一行字符串(CRLF结尾)
		 * 2:根据空格拆分(\s)
		 * 3:将请求行中三项内容设置到HttpRequest
		 *   对应属性上
		 *   
		 * GET /index.html HTTP/1.1  
		 * GET /reg?username=fancq&password=123456&nickname=fanfan HTTP/1.1  
		 */
		try{
			//1
			String line = readLine(in);
			System.out.println("requestLine:"+line);
			/*
			 * 对请求行格式做一些必要验证
			 */
			if(line.length()==0){
				throw new RuntimeException("空的请求行!");
			}
			//2
			String[] data = line.split("\\s");
			
			//3
			this.method = data[0];
//			this.uri = data[1];
			parseUri(data[1]);
			this.protocol = data[2];
			System.out.println("解析请求行完毕!");
		}catch(IOException e){
			throw e;
		}
	}
	/**
	 * 处理URI
	 * @param uri
	 */
	private void parseUri(String uri){
		/*
		 * /index.html
		 * /reg?username=fancq&password=123456&nickname=fanfan
		 * 在GET请求中，URI可能会有上面两种情况。
		 * HTTP协议中规定，GET请求中的URI可以传递
		 * 参数，而规则是请求的资源后面以"?"分割，
		 * 之后则为所有要传递的参数，每个参数由:
		 * name=value的格式保存，每个参数之间使用
		 * "&"分割。
		 * 这里的处理要求:
		 * 将"?"之前的内容保存到属性uri上。
		 * 之后的每个参数都存入属性parameters中
		 * 其中key为参数名，value为参数的值。
		 * 
		 * 1:实例化HashMap用于初始化属性parameters
		 * 
		 */
		//1
		this.parameters = new HashMap<String,String>();
		
		//先分析uri中是否含有"?"
		int index = -1;
		if((index = uri.indexOf("?"))>=0){
			//先按照"?"拆分
			this.uri = uri.substring(0,index);
			
			/*
			 * 截取出所有参数
			 * paras:username=fancq&password=123456&nickname=fanfan
			 */
			String paras = uri.substring(index+1);
			/*
			 * 拆分每一个参数
			 * [username=fancq,password=123456,nickname=fanfan]
			 */
			String[] paraArray = paras.split("&");
			//遍历每一个参数
			for(String para : paraArray){
				//username=
				//按照"="拆分
				String[] paraDate = para.split("=");
				if(paraDate.length>0){
					if(paraDate.length==1){
						this.parameters.put(paraDate[0], null);
					}else{
						this.parameters.put(paraDate[0], paraDate[1]);
					}
				}
				
			}
		}else{
			this.uri = uri;
		}
		
		
	}
	
	/**
	 * 解析消息头
	 * @param in
	 * @throws Exception 
	 */
	private void parseRequestHeader(InputStream in) throws Exception{
		/*
		 * 消息头由若干行组成
		 * 每行格式:
		 * name:valueCRLF
		 * 当所有消息头全部发送过来后，浏览器会
		 * 单独发送一个CRLF结束
		 * 
		 * 实现思路:
		 * 1:死循环下面步骤
		 * 2:读取一行字符串
		 * 3:判断该字符串是否为空字符串
		 *   若是空字符串说明读到最后单独的CRLF
		 *   那么就可以停止循环，不用再解析消息头
		 *   信息
		 * 4:若不是空串，则按照“:”截取出名字与
		 *   对应的值并存入header这个Map中
		 */
		try {
			header = new HashMap<String,String>();
			String line = null;
			while(true){
				line = readLine(in);
				if(line.length()==0){
					break;
				}
				int index = line.indexOf(":");
				String name = line.substring(0,index);
				String value = line.substring(index+1).trim();
				header.put(name, value);
			}
		} catch (Exception e) {
			throw e;
		}
		System.out.println("header:"+header);
		System.out.println("解析消息头完毕!");
	}
	
	
	/**
	 * 根据输入流读取一行字符串
	 * 根据HTTP协议读取请求中的一行内容,
	 * 以CRLF结尾的一行字符串
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private String readLine(InputStream in)
							throws IOException{
		//请求中第一行字符串(请求行内容)
		StringBuilder builder 
			= new StringBuilder();
		/*
		 * 连续读取若干字符，直到连续读取到了CRLF为止
		 */
		//c1是本次读到的字符，c2是上次读到的字符
		int c1 = -1, c2 = -1;
		while((c1 = in.read())!=-1){
			if(c1==HttpContext.LF&&c2==HttpContext.CR){
				break;
			}
			builder.append((char)c1);
			c2 = c1;
		}
		return builder.toString().trim();
	}
	
	public String getMethod() {
		return method;
	}
	public String getUri() {
		return uri;
	}
	public String getProtocol() {
		return protocol;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	/**
	 * 根据参数名获取参数值
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		return parameters.get(name);
	}
	
	
	public HttpRequest(){
		
	}	
	public static void main(String[] args) {
		HttpRequest r = new HttpRequest();
		r.parseUri("/reg?username=fancq&password=123456&nickname=fanfan");
		System.out.println("uri:"+r.uri);
		System.out.println("param:"+r.parameters);
	}
	
}








