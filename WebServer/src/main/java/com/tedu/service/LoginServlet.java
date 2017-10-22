package com.tedu.service;

import com.tedu.core.HttpServlet;
import com.tedu.dao.UserInfoDAO;
import com.tedu.db.DBUtil;
import com.tedu.entities.UserInfo;
import com.tedu.http.HttpRequest;
import com.tedu.http.HttpResponse;

public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("开始登录...");
		//获取用户名，密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username+","+password);
		try{
			UserInfoDAO dao = new UserInfoDAO();
			UserInfo userInfo = dao.findByUsernameAndPassword(username, password);
			if(userInfo!=null){
				System.out.println("登录成功!");
				/*
				 * 响应登录成功的页面给客户端
				 */
				forward("/login_ok.html", response);
			}else{
				System.out.println("登录失败!");
				forward("/login_error.html", response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}






