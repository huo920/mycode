package com.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.client.vo.Response_Command;
import com.client.vo.TalkWindowInfo;
//此线程体用于获取来自服务器端的信息
public class GetServerInfo implements Runnable{
	private InputStream in;
	private LinkInfo linkInfo;
	public GetServerInfo(LinkInfo linkInfo){
		try {
			this.linkInfo = linkInfo;
			//获取来自服务器端的输入流，并包装成高级流BufferedReader,已用于直接处理字符串
			in = linkInfo.getSocket().getInputStream();
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}		
	}
	public void run() {
		try{
			int command = -1;
			String fromuser;
			while((command = in.read())!=-1){
				switch(command){				
					case Response_Command.REG_RESPONSE:
						String info = IOUtil.readString(in);
						if(info.equals("regSuccess")){
							System.out.println("注册成功");
//							提示注册成功信息
							linkInfo.getAlert().showAlert("<html><center>注册成功！欢迎您"+this.linkInfo.getMe()+"</center></html>");
							TalkingMain tm = new TalkingMain();
							tm.init(this.linkInfo);
							this.linkInfo.getReg().closeFrame();
						}else{
							//提示错误信息
							linkInfo.getAlert().showAlert("<html><center><h1>注册失败</h1>原因:"+info+"</center></html>");
							linkInfo.getReg().jButtonReg.setEnabled(true);//使注册按钮可用
							linkInfo.getReg().jButtonLogin.setEnabled(true);//使去登陆按钮可用
						}
						break;
					case Response_Command.LOGIN_RESPONSE:
						String info1 = IOUtil.readString(in);
						if(info1.equals("loginSuccess")){
							TalkingMain tm = new TalkingMain();
							tm.init(this.linkInfo);
							this.linkInfo.getLogin().closeFrame();
						}else{
							linkInfo.getAlert().showAlert("<html><center><h1>登陆失败</h1>原因:"+info1+"</center></html>");
							linkInfo.getLogin().jButtonLogin.setEnabled(true);//使登陆按钮可用
							linkInfo.getLogin().jButtonReg.setEnabled(true);//使去注册按钮可用
						}
						break;
					case Response_Command.USER_LIST_RESPONSE:
						String userLine = IOUtil.readString(in);
						String []friends = userLine.split(",");
						if(friends!=null&&friends.length>0){
							ArrayList <String>friendUserInfos = new ArrayList<String>();
							linkInfo.setFriendUserInfos(friendUserInfos);
							for(int i =0;i<friends.length;i++){
								friendUserInfos.add(friends[i]);
							}
							//将所有用户显示到主界面去==>TalkingMain
							this.linkInfo.getTalkingMain().showAllFriend(friendUserInfos);
						}
						break;
					case Response_Command.MESSAGE_TO_ONE_RESPONSE:
						fromuser = IOUtil.readString(in);
						String message = IOUtil.readString(in);
						TalkWindow tw = getTalkWindowById(fromuser);
						tw.showMessage(message);
						break;
					case Response_Command.MESSAGE_TO_ALL_RESPONSE:
						fromuser = IOUtil.readString(in);
						String messages = IOUtil.readString(in);
						TalkWindow twAll = getTalkWindowById("ALL");
						twAll.showAllMessage(fromuser,messages);
					
						break;
					case Response_Command.FILE_TO_ONE_RESPONSE:
						fromuser = IOUtil.readString(in);
						String fileName = IOUtil.readString(in);
						TalkWindow t = getTalkWindowById(fromuser);
						t.alertFileResponse(fromuser,fileName);
						break;
					case Response_Command.FILE_TO_ONE_1_RESPONSE:
						fromuser = IOUtil.readString(in);
						String return_info = IOUtil.readString(in);
						TalkWindow t1 = getTalkWindowById(fromuser);
						t1.sendFile1(return_info,fromuser);		
						break;
					case Response_Command.FILE_TO_ONE_2_RESPONSE:
						fromuser = IOUtil.readString(in);	
						TalkWindow t2 = getTalkWindowById(fromuser);
						t2.getFile();		
						break;
//					//显示聊天内容
//					if(info.equals("message")){
//						String messageInfo[] = infos[1].split(Tools.getSysTagModel("from"));//根据from拆分信息来源于那个用户的(只有ID值)
//						TalkWindow tw = getTalkWindowById(messageInfo[0]);
//						tw.showMessage(messageInfo[1].replace(Tools.getSysTagModel("n"),"\n"));
//					}
//					//显示用户列表到主程序上
//					else if(infos[0].equals("showAllFriends")){
//						//使用;号截取每个用户
//						String []friends = infos[1].split(Tools.getSysTagModel(";"));
//						if(friends!=null&&friends.length>0){
//							ArrayList <UserInfo>friendUserInfos = new ArrayList<UserInfo>();
//							linkInfo.setFriendUserInfos(friendUserInfos);
//							for(int i =0;i<friends.length;i++){
//								//用,拆分用户的相信信息
//								String []friend = friends[i].split(Tools.getSysTagModel(","));
//								UserInfo fri = new UserInfo(friend[0],friend[1]);
//								friendUserInfos.add(fri);
//							}
//							//将所有用户显示到主界面去==>TalkingMain
//							this.linkInfo.getTalkingMain().showAllFriend(friendUserInfos);
//						}
//					}
//					//登录成功的操作
//					else if(infos[0].equals("loginSuccess")){
//						this.linkInfo.getMe().setUsername(infos[1]);
//						TalkingMain tm = new TalkingMain();
//						tm.init(this.linkInfo);
//						this.linkInfo.getLogin().closeFrame();
//					}
//					//登录失败的操作
//					else if(infos[0].equals("loginError")){
//						linkInfo.getAlert().showAlert("<html><center><h1>登陆失败</h1>原因:"+infos[1]+"</center></html>");
//						linkInfo.getLogin().jButtonLogin.setEnabled(true);//使登陆按钮可用
//						linkInfo.getLogin().jButtonReg.setEnabled(true);//使去注册按钮可用
//					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			//如果与服务器断开连接
			//提示用户
			linkInfo.getAlert().showAlert("<html><center><h1>与服务器断开连接</h1>原因:这有可能是网络原因，或服务器端异常导致</center></html>");
			//关闭主窗体
			linkInfo.getTalkingMain().closeFrame();
			//关闭所有已打开的聊天窗体
			for(int i =0;i<linkInfo.getAllTalkWindowInfos().size();i++){
				linkInfo.getAllTalkWindowInfos().get(i).getTalkingWindow().closeFrame();
			}
			//清空聊天窗体信息
			linkInfo.getAllTalkWindowInfos().clear();
			//打开登陆框
			linkInfo.setLogin(new Login(this.linkInfo));
		}
	}
	//根据好友ID获取此好友的聊天窗口
	public TalkWindow getTalkWindowById(String user){
		//判断与此好友聊天的窗口是否已经打开
		for(int i =0;i<this.linkInfo.getAllTalkWindowInfos().size();i++){
			if(this.linkInfo.getAllTalkWindowInfos().get(i).getTargetUserInfo().equals(user)){
				return this.linkInfo.getAllTalkWindowInfos().get(i).getTalkingWindow();
			}
		}
		//如果此好友的聊天窗口没有打开，则自动打开一个与此好友聊天的聊天窗口
		TalkWindow talkWindow = null;
		
		//若是群聊
		if("ALL".equals(user)){
			talkWindow = new TalkWindow(linkInfo.getMe(),"ALL",linkInfo);
			linkInfo.getAllTalkWindowInfos().add(new TalkWindowInfo(talkWindow,"ALL"));
			return talkWindow;
		}
		
		for(int i =0;i<linkInfo.getFriendUserInfos().size();i++){
			if(linkInfo.getFriendUserInfos().get(i).equals(user)){
				talkWindow = new TalkWindow(linkInfo.getMe(),linkInfo.getFriendUserInfos().get(i),linkInfo);
				linkInfo.getAllTalkWindowInfos().add(new TalkWindowInfo(talkWindow,linkInfo.getFriendUserInfos().get(i)));
			}
		}		
		return talkWindow;
	}
}
