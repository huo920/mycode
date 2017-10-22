package com.tedu.core;

import java.io.File;

import com.tedu.common.HttpContext;
import com.tedu.http.HttpResponse;

/**
 * 用来处理Http的功能请求
 * @author adminitartor
 *
 */
public class HttpServlet {
	public void forward(String uri,HttpResponse response) throws Exception{
		File file = new File(
			"webapp"+uri);
		//设置状态行
		response.setStatus(HttpContext.STATUS_CODE_OK);
		//设置响应头
		response.setContentType("text/html");
		response.setContentLength((int)file.length());
		//设置响应正文
		response.setEntity(file);
		response.flush();
	}
}
