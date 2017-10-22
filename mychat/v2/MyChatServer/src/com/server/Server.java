package com.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.server.vo.UserInfo;

public class Server {
	//系统所有用户的信息
	private HashMap<String,UserInfo> users;
	public Server(){
		users = new HashMap<String,UserInfo>();
	}
	/**
	 * 服务器启动方法
	 */
	public void start(){
		try {
			System.out.println("正在启动服务器");
			ServerSocket ss = new ServerSocket(7788);
			System.out.println("启动服务器完毕");
			while(true){
				DoServiceForClient dsfc = new DoServiceForClient(ss.accept());
				new Thread(dsfc).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String []args){
		Server server = new Server();
		server.start();
	}
	
	class DoServiceForClient implements Runnable{
		//当前连接客户端的套接字
		private Socket client;
		//当前客户端的输入流
		private InputStream in;
		//当前客户端的输出流
		private OutputStream out;
		//当前客户端对应的用户信息
		private UserInfo userinfo;
		
		//构造方法
		public DoServiceForClient(Socket client){
			this.client = client;
		}
		//线程体
		public void run() {
			try {
				//获取当前连接客户端的输入流，用于获取来自客户端的信息
				in = client.getInputStream();
				out = client.getOutputStream();
				int command = -1;
				while((command = in.read())!=-1){
					switch(command){
						//登陆操作
						case Request_Command.LOGIN:
							System.out.println("message:登陆操作");
							doLogin();
							break;
						//注册操作
						case Request_Command.REG:
							System.out.println("message:注册操作");
							doReg();
							break;
						//广播文本信息
						case Request_Command.SEND_TEXT:
							System.out.println("message:广播消息");
							sendMessageToAll();
							break;
						//文本私聊
						case Request_Command.SEND_TEXT_TO_ONE:
							System.out.println("message:私聊消息");
							sendMessageToOne();
							break;
						//广播文件
						case Request_Command.SEND_FILE:
							break;
						//请求个人传送文件
						case Request_Command.SEND_FILE_TO_ONE:
							System.out.println("message:请求传送文件至私人");
							sendFileToOne();
							break;
						//请求回复发件人结果
						case Request_Command.SEND_FILE_TO_ONE_1:
							System.out.println("message:回复是否接收文件");
							sendFileToOne1();
							break;
						//请求开始传送文件
						case Request_Command.SEND_FILE_TO_ONE_2:
							System.out.println("message:开始向私人传送文件");
							sendFileToOne2();
							break;
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				//从共享中删除当前用户
				users.remove(userinfo.getUsername());
				//刷新所有客户端的好友列表
				try {
					reshowFriendList();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		/**
		 * 开始发送文件
		 */
		public void sendFileToOne2()throws Exception{
			try{
				String target = IOUtil.readString(in);
				long len = IOUtil.readLong(in);
				byte[] buf = new byte[1024];	
				//本次读取的字节量
				int loaded = (int)(1024<len?1024:len);
				//总共读取的字节量
				long sum=0;
				OutputStream out = users.get(target).getSocket().getOutputStream();
				IOUtil.writeShort(Response_Command.FILE_TO_ONE_2_RESPONSE, out);
				IOUtil.writeString(userinfo.getUsername(), out);
				IOUtil.writeLong(len, out);
				while(true){
					in.read(buf,0,loaded);
					out.write(buf,0,loaded);
					sum+=loaded;
					loaded = (int)(sum+1024<len?1024:len-sum);
					if(sum==len){
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
				
		}
		/**
		 * 请求回复发件人结果
		 */
		public void sendFileToOne1()throws Exception{
			String target = IOUtil.readString(in);
			String return_info = IOUtil.readString(in);
			try {
				if("OK".equals(return_info)){
					OutputStream out = users.get(target).getSocket().getOutputStream();
					IOUtil.writeShort(Response_Command.FILE_TO_ONE_1_RESPONSE, out);
					IOUtil.writeString(userinfo.getUsername(), out);
					IOUtil.writeString("OK", out);
				}else{
					OutputStream out = users.get(target).getSocket().getOutputStream();
					IOUtil.writeShort(Response_Command.FILE_TO_ONE_1_RESPONSE, out);
					IOUtil.writeString(userinfo.getUsername(), out);
					IOUtil.writeString("NO", out);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		/**
		 * 给收件人通知，询问是否接收文件
		 */
		public void sendFileToOne()throws Exception{
			String target = IOUtil.readString(in);
			String fileName = IOUtil.readString(in);
			try {
				OutputStream out = users.get(target).getSocket().getOutputStream();
				IOUtil.writeShort(Response_Command.FILE_TO_ONE_RESPONSE, out);
				//发送当前用户给目标
				IOUtil.writeString(userinfo.getUsername(), out);
				//发送文件名
				IOUtil.writeString(fileName, out);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		/**
		 * 广播功能
		 */
		public void sendMessageToAll()throws Exception{
			//获取消息
			String message = IOUtil.readString(in);
			//获取对方输出流
			try {
				for(UserInfo userInfo : users.values()){
					//不用转发给自己
					if(userInfo.getUsername().equals(userinfo.getUsername())){
						continue;
					}
					OutputStream out = userInfo.getSocket().getOutputStream();
					IOUtil.writeShort(Response_Command.MESSAGE_TO_ALL_RESPONSE, out);
					//发送当前用户给目标
					IOUtil.writeString(userinfo.getUsername(), out);
					IOUtil.writeString(message, out);
				}
			} catch (Exception e) {
				throw e;
			}
			
			
		}
		/**
		 * 私聊功能
		 */
		public void sendMessageToOne()throws Exception{
			//获取聊天对象
			String target = IOUtil.readString(in);
			//获取消息
			String message = IOUtil.readString(in);
			//获取对方输出流
			try {
				OutputStream out = users.get(target).getSocket().getOutputStream();
				IOUtil.writeShort(Response_Command.MESSAGE_TO_ONE_RESPONSE, out);
				//发送当前用户给目标
				IOUtil.writeString(userinfo.getUsername(), out);
				IOUtil.writeString(message, out);
			} catch (Exception e) {
				throw e;
			}
			
			
		}
		
		//刷新所有用户的好友列表
		public void reshowFriendList()throws Exception{
			StringBuffer friendsInfo=new StringBuffer();
			for(String user:users.keySet()){
				friendsInfo.append(user+",");
			}
			for(UserInfo userinfo : users.values()){
				
				try{
					IOUtil.writeShort(Response_Command.USER_LIST_RESPONSE, userinfo.getSocket().getOutputStream());
					IOUtil.writeString(friendsInfo.toString(), userinfo.getSocket().getOutputStream());
				}catch(Exception e){
					throw e;
				}
			}
		}
		
		//注册方法
		public void doReg()throws Exception{
			try{
				//获取用户名
				String username = IOUtil.readString(in);
				//获取密码
				String password = IOUtil.readString(in);
				DBManager.addUser(new UserInfo(username,password));
				//存入共享
				userinfo = new UserInfo();
				userinfo.setUsername(username);
				userinfo.setPassword(password);
				userinfo.setSocket(client);
				users.put(username, userinfo);
			}catch(Exception e){
				//发送响应
				IOUtil.writeShort(Response_Command.REG_RESPONSE, out);
				IOUtil.writeString(e.getMessage(), out);
				throw e;
			}
			//发送响应
			IOUtil.writeShort(Response_Command.REG_RESPONSE, out);
			IOUtil.writeString("regSuccess", out);
			//刷新所有客户端的好友列表
			reshowFriendList();
			
		}
		
		//登陆方法
		public void doLogin()throws Exception{
			try{
				//获取用户名
				String username = IOUtil.readString(in);
				//获取密码
				String password = IOUtil.readString(in);
				if(!checkUserInfoHasLoginByUserName(username)){	
					UserInfo user = new UserInfo(username,password);
					if(DBManager.login(user)){
						userinfo = user;
						userinfo.setSocket(client);
						//存入共享
						users.put(username,userinfo);
						//发送响应
						IOUtil.writeShort(Response_Command.LOGIN_RESPONSE, out);
						IOUtil.writeString("loginSuccess", out);	
						//刷新所有客户端的好友列表
						reshowFriendList();
					}else{
						IOUtil.writeShort(Response_Command.LOGIN_RESPONSE, out);
						IOUtil.writeString("用户名或密码错误...", out);
					}
				}else{
					IOUtil.writeShort(Response_Command.LOGIN_RESPONSE, out);
					IOUtil.writeString("账号:"+username+" 处于登陆状态...", out);
				}
			}catch(Exception e){
				IOUtil.writeShort(Response_Command.LOGIN_RESPONSE, out);
				IOUtil.writeString("登陆异常"+e.getMessage(), out);
				throw e;
			}
		}
		//判断当前用户是否处于登陆状态
		public boolean checkUserInfoHasLoginByUserName(String username){
			return users.containsKey(username);
		}
		
	}
}
