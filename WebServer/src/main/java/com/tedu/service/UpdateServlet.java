package com.tedu.service;

import com.tedu.core.HttpServlet;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;
/**
 * 完成用户修改信息的请求
 * @author adminitartor
 *
 */
public class UpdateServlet extends HttpServlet {
	public void service(HttpRequest request,HttpResponse response){
		/*
		 * 1:获取用户输入的用户名等信息
		 * 2:先判断该用户是否存在，不存在则
		 *   提示用户(跳转没有该用户的提示页面)
		 * 3:将用户输入的信息存入一个UserInfo
		 *   实例中
		 * 4:调用UserInfoDAO的update进行修改
		 * 5:若修改成功跳转修改成功的提示页面
		 *   否则跳转失败的提示页面  
		 * 
		 */
	}
}






