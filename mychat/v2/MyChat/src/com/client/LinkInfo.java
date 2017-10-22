package com.client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.client.vo.TalkWindowInfo;
//客户端需要的所有信息
public class LinkInfo {
	//警告框
	private Alert alert;
	//登陆框
	private Login login;
	//注册框
	private Reg reg;
	//主框
	private TalkingMain talkingMain;
	private Socket socket;
	private ResourceBundle resourceBundle;
	//当前用户
	private String me = new String();
	//所有好友信息
	private ArrayList <String>friendUserInfos;
	//已打开的对话窗口信息集合
	private ArrayList<TalkWindowInfo> allTalkWindowInfos = new ArrayList<TalkWindowInfo>();
	
	public ArrayList<TalkWindowInfo> getAllTalkWindowInfos() {
		return allTalkWindowInfos;
	}

	public void setAllTalkWindowInfos(ArrayList<TalkWindowInfo> allTalkWindowInfos) {
		this.allTalkWindowInfos = allTalkWindowInfos;
	}

	public String getMe() {
		return me;
	}

	public void setMe(String me) {
		this.me = me;
	}

	//过去配置文件信息
	public String getConfigValueByKey(String key){
		return (String)resourceBundle.getObject(key);
	}
	
	public boolean initSocket(){
		try{
			if(socket==null){
				int host = Integer.parseInt(this.getConfigValueByKey("serverHost"));
				this.socket = new Socket(this.getConfigValueByKey("serverLocal"),host);
				return true;
			}
		}catch(Exception e){
			System.out.println(e);
			this.alert.showAlert("连接服务器失败..请检查网络是否连接正常后再试.");
		}		
		return false;
	}
	
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Alert getAlert() {
		return alert;
	}

	public void setAlert(Alert alert) {
		this.alert = alert;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public TalkingMain getTalkingMain() {
		return talkingMain;
	}

	public void setTalkingMain(TalkingMain talkingMain) {
		this.talkingMain = talkingMain;
	}

	public ArrayList<String> getFriendUserInfos() {
		return friendUserInfos;
	}

	public void setFriendUserInfos(ArrayList<String> friendUserInfos) {
		this.friendUserInfos = friendUserInfos;
	}

	public Reg getReg() {
		return reg;
	}

	public void setReg(Reg reg) {
		this.reg = reg;
	}
}
