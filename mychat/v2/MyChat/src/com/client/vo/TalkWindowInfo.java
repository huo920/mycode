package com.client.vo;

import com.client.TalkWindow;

//已打开的对话窗口信息
public class TalkWindowInfo {
	private TalkWindow talkingWindow;//对话窗口实例
	private String targetUserInfo;//聊天对象的信息
	public TalkWindowInfo(){
		
	}
	
	public TalkWindowInfo(TalkWindow talkingWindow,String targetUserInfo){
		this.talkingWindow = talkingWindow;
		this.targetUserInfo = targetUserInfo;
	}
	
	public TalkWindow getTalkingWindow() {
		return talkingWindow;
	}
	public void setTalkingWindow(TalkWindow talkingWindow) {
		this.talkingWindow = talkingWindow;
	}
	public String getTargetUserInfo() {
		return targetUserInfo;
	}
	public void setTargetUserInfo(String targetUserInfo) {
		this.targetUserInfo = targetUserInfo;
	}
	
}
