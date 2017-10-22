package day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 指定的DQL语句
 */
public class JDBCDemo5 {

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.0.113:1521:xe",
					"system",
					"123456");
			System.out.println("建立连接");
			Statement state = conn.createStatement();
			
			
			/*
			 * SELECT id,username,password,nickname,,accout
			 * FROM userinfo_huo
			 */
			String sql = "SELECT id,username,password,nickname,account "+
							"FROM userinfo_huo";
			
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String nickname = rs.getString("nickname");
				int account = rs.getInt("account");
				
				System.out.println(id+","+username+","+password+","+nickname+","+account);
			}
			
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
