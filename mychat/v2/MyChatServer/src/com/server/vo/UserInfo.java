package com.server.vo;

import java.net.Socket;

public class UserInfo {
	private String username;
	private String password;
	private Socket socket;//当前用户的套接字

	public UserInfo() {
	}
		
	public UserInfo(String username, String password) {
		this.username = username;
		this.password = password;
	}


	public UserInfo(String data) {
		String[] datas = data.split(",");
		if(datas==null||datas.length<2){
			return;
		}
		username = datas[0];
		password = datas[1];
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public String toString() {
		return username+","+password;
	}
}
