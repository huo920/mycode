package day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 输入一个用户名，然后将该用户删除
 * @author tedu
 *
 */
public class JDBCDemo4 {
	public static void main(String[] args) {
		try {
			Scanner scan = new Scanner(System.in);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.0.113:1521:xe",
					"system",
					"123456");
			
			
			System.out.println("已经建立连接");
			Statement state = conn.createStatement();
			System.out.println("请输入要删除的用户名：");
			String username = scan.nextLine();
			/*
			 * DELETE userinfo_huo
			 * WHERE name = ''
			 * 
			 */
			String sql = "DELETE FROM userinfo_huo "+
						"WHERE username = '"+username+"'";
			int d = state.executeUpdate(sql);
			if(d>0){
				
				System.out.println("删除成功");
			}
			conn.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
