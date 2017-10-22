package com.tedu.entities;
/**
 * UserInfo类用于表示userinfo表
 * 其每一个实例用于表示userinfo表中的一条记录
 * @author adminitartor
 *
 */
public class UserInfo {
	private int id;
	private String username;
	private String password;
	private String nickname;
	private int account;
	
	public UserInfo(){
	}

	public UserInfo(int id, String username, String password, String nickname, int account) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.account = account;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}
	
	public String toString() {
		return id+","+username+","+password+","+nickname+","+account;
	}
}






