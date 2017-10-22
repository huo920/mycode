package com.client;

import java.util.Locale;
import java.util.ResourceBundle;


public class Talking {
	private LinkInfo linkInfo = new LinkInfo();
	public static void main(String []args){
		Talking talk = new Talking();
		//加载配置文件
		talk.linkInfo.setResourceBundle(ResourceBundle.getBundle("init",
					Locale.getDefault()));
		//创建提示窗口
		talk.linkInfo.setAlert(new Alert());
		talk.showLogin();
	}
	//显示登陆窗口
	public void showLogin(){
		linkInfo.setLogin(new Login(linkInfo));
	}
}
