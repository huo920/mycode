package com.tedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.tedu.db.DBUtil;
import com.tedu.entities.UserInfo;

/**
 * DAO 数据连接对象
 * DAO是一个层次，该层里的所有类都是和数据库打
 * 交到的，作用是将数据操作的功能从业务逻辑层
 * 中分离出来，使得业务逻辑层更专注的处理业务
 * 操作，而对于数据的维护操作分离到DAO中。
 * 并且DAO与业务逻辑层之间是用JAVA中的对象来
 * 传递数据，这也使得有了DAO可以让业务逻辑层
 * 对数据的操作完全面向对象化。
 * @author adminitartor
 *
 */
public class UserInfoDAO {
	/**
	 * 
	 * 修改给定的用户信息
	 * @param userInfo
	 * @return
	 */
	public boolean update(UserInfo userInfo){
		/*
		 * 名字和ID不可修改，可以根据用户名
		 * 修改userInfo中该用户的新密码，昵称
		 * 以及余额
		 * UPDATE userinfo 
		 * SET password=?,nickname=?,account=?
		 * WHERE username=?
		 */
		return false;
	}
	
	/**
	 * 根据给定的用户名和密码查询该用户
	 * @param username
	 * @param password
	 * @return
	 */
	public UserInfo findByUsernameAndPassword(String username,String password){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT id,username,password,nickname,account "
					   + "FROM userinfo "
					   + "WHERE username=? AND password=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				int id = rs.getInt("id");
				username = rs.getString("username");
				password = rs.getString("password");
				String nickname = rs.getString("nickname");
				int account = rs.getInt("account");
				UserInfo userInfo = new UserInfo(id, username, password, nickname, account);
				return userInfo;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeConnection(conn);
		}
		return null;
		
	}
	
	/**
	 * 根据给定的用户名查找该用户
	 * @param username
	 * @return 若没有此用户则返回值为NULL
	 */
	public UserInfo findByUsername(String username){
		/*
		 * 根据给定的用户名查询该用户信息，若没有
		 * 记录则直接返回NULL，若查询到将该条记录
		 * 各个字段的值取出来存入到一个UserInfo实例
		 * 中并返回。
		 */
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT id,username,password,nickname,account "
					   + "FROM userinfo "
					   + "WHERE username=?";
			PreparedStatement ps
				=	conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				int id = rs.getInt("id");
				username = rs.getString("username");
				String password = rs.getString("password");
				String nickname = rs.getString("nickname");
				int account = rs.getInt("account");
				UserInfo userInfo = new UserInfo(id, username, password, nickname, account);
				return userInfo;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeConnection(conn);
		}
		return null;
	}
	
	/**
	 * 保存给定的UserInfo对象所表示的用户信息
	 * @param userInfo
	 * @return
	 */
	public boolean save(UserInfo userInfo){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "INSERT INTO userinfo "
					   + "(id,username,password,nickname,account) "
					   + "VALUES "
					   + "(seq_userinfo_id.NEXTVAL,?,?,?,?)";
			PreparedStatement ps 
				= conn.prepareStatement(sql,new String[]{"id"});
			ps.setString(1, userInfo.getUsername());
			ps.setString(2, userInfo.getPassword());
			ps.setString(3, userInfo.getNickname());
			ps.setInt(4, userInfo.getAccount());
			int d = ps.executeUpdate();
			if(d>0){
				//插入数据成功
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				int id = rs.getInt(1);
				userInfo.setId(id);
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			DBUtil.closeConnection(conn);
		}
		return false;
	}
	
	public static void main(String[] args) {
		UserInfoDAO dao = new UserInfoDAO();
		UserInfo userinfo = dao.findByUsername("jack");
		System.out.println(userinfo);
	}
}









