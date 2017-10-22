package com.wx.auth.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.auth.util.AuthUtil;

import net.sf.json.JSONObject;

@WebServlet("/callBack")
public class CallBackServlet extends HttpServlet{
	private String driverClass;
	private String url;
	private String username;
	private String password;
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		try {
			this.driverClass = config.getInitParameter("driverClass");
			this.url = config.getInitParameter("url");
			this.username = config.getInitParameter("username");
			this.password = config.getInitParameter("password");
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AuthUtil.APPID
				+ "&secret="+AuthUtil.APPSECRET
				+ "&code="+code
				+ "&grant_type=authorization_code";
		
		JSONObject jsonObject = AuthUtil.doGetJson(url);
		String openid = jsonObject.getString("openid");
		String access_token = jsonObject.getString("access_token");
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token
				+ "&openid="+openid
				+ "&lang=zh_CN";
		JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
		System.out.println(userInfo);
		
		//1.使用微信用户信息直接登录,无需注册和绑定
		req.setAttribute("info", userInfo);
		req.getRequestDispatcher("/index1.jsp").forward(req, resp);
		
		//2.将微信与系统当前账号进行绑定
//		try {
//			String nickName = getNickName(openid);
//			if(!"".equals(nickName)){
//				//已经绑定
//				req.setAttribute("nickName", nickName);
//				req.getRequestDispatcher("/index2.jsp").forward(req, resp);
//			}else{
//				//未绑定
//				req.setAttribute("openid", openid);
//				req.getRequestDispatcher("/login.jsp").forward(req, resp);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
	}
	
	
	public String getNickName(String openid) throws SQLException{
		String nickName = "";
		conn = DriverManager.getConnection(url,username,password);
		String sql = "select nickname from user where openid=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, openid);
		rs = ps.executeQuery();
		while(rs.next()){
			nickName = rs.getString("NICKNAME");
		}
		conn.close();
		return nickName;
	}
	
	public int updateUser(String openid,String account,String password) throws SQLException{
		conn = DriverManager.getConnection(url,username,password);
		String nickName = "";
		String sql = "update user set OPENID=? where ACCOUNT=? and PASSWORD=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, openid);
		ps.setString(2, account);
		ps.setString(3, openid);
		int d = ps.executeUpdate();
		conn.close();	
		return d;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String openid = req.getParameter("openid");
		try {
			int d = updateUser(openid, account, password);
			if(d>0){
				System.out.println("账号绑定成功");
			}else{
				System.out.println("账号绑定失败");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
