package com.server;

public class Tools {
	//为系统分隔符额外加一些字符，使其不容易与聊天内容发生混淆(此方法添加的额外字符应该与客户端的Tools的方法一致)
	public static String getSysTagModel(String tag){
		return "--"+tag+"--";
	}
}
