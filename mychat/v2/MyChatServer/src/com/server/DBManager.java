package com.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import com.server.vo.UserInfo;

public class DBManager {
	private static HashMap<String,UserInfo> users = new HashMap<String,UserInfo>();
	private static File userdata = new File("user.dat");

	/**
	 * 注册新用户
	 * 
	 * @return
	 */
	public static synchronized void addUser(UserInfo userInfo)throws Exception {
		//若用户名已存在
		if(users.containsKey(userInfo.getUsername())){
			throw new Exception("该用户名已经被使用");
		}		
		try {
			//添加新用户
			FileOutputStream fos
				= new FileOutputStream(userdata,true);
			OutputStreamWriter osw
				= new OutputStreamWriter(fos,"utf-8");
			PrintWriter pw
				= new PrintWriter(osw);
			pw.println(userInfo);
			pw.close();
		} catch (Exception e) {
			throw new Exception("注册失败");
		}
		//注册后刷新缓存
		users.put(userInfo.getUsername(), userInfo);
	}
	
	public static boolean login(UserInfo userInfo){
		UserInfo user = users.get(userInfo.getUsername());
		//没这个用户
		if(user == null){
			return false;
		}
		//密码不对
		if(!user.getPassword().equals(userInfo.getPassword())){
			return false;
		}
		//成功
		return true;
	}
	
	
	// 静态块用于初始化
	static {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 */
	private static void init() {
		try {
			if (!userdata.exists()) {
				userdata.createNewFile();
			}
			loadUsers();
		} catch (Exception e) {

		}
	}

	/**
	 * 读取所有用户
	 */
	private synchronized static void loadUsers() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("user.dat"), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				UserInfo user = new UserInfo(line);
				users.put(user.getUsername(),user);
			}
		} catch (Exception e) {

		}
	}

}
