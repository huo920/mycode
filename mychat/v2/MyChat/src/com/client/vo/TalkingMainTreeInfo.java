package com.client.vo;

import javax.swing.tree.DefaultMutableTreeNode;

public class TalkingMainTreeInfo {
	private String userInfo;//用户
	private DefaultMutableTreeNode dmtn;//此用户对应的树的节点
	public TalkingMainTreeInfo(){
		
	}
	public TalkingMainTreeInfo(String userInfo,DefaultMutableTreeNode dmtn){
		this.userInfo = userInfo;
		this.dmtn = dmtn;
	}
	public DefaultMutableTreeNode getDmtn() {
		return dmtn;
	}
	public void setDmtn(DefaultMutableTreeNode dmtn) {
		this.dmtn = dmtn;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	
}
