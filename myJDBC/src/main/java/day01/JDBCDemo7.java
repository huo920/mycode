package day01;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 完成登录功能
 * 要求用户输入用户名和密码
 * 若userinfo表中有对应数据，则显示登陆成功
 * 否则显示登录失败
 * @author tedu
 *
 */
public class JDBCDemo7 {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			System.out.println("连接数据库");
			
			Scanner scan = new Scanner(System.in);
			System.out.println("请输入用户名：");
			String username = scan.nextLine();
			System.out.println("请输入密码：");
			String password = scan.nextLine();
			
			
			
			String sql = "SELECT id,username,password,nickname,account "+
							"FROM userinfo_huo "+
							"WHERE username='"+username+"' AND password='"+password+"' ";
			/*
			 * 存在危险，SQL注入攻击
			 * 
			 * 请输入用户名：
			 * sdfgafdg
			 * 请输入密码：
			 * asdfasf' OR '1'='1
			 * 
			 * 
			 * SELECT id,username,password,nickname,account 
			 * FROM userinfo_huo 
			 * WHERE username='sdfgafdg' 
			 * AND password='asdfasf' 
			 * OR '1'='1' 
             * 登录成功
			 */
			
			System.out.println(sql);
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			if(rs.next()){
				System.out.println("登录成功");
			}else{
				System.out.println("用户名或密码错误");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
			
		}
	}

}
