package day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 向表中插入一条记录
 * @author tedu
 *
 */
public class JDBCDemo3 {
	public static void main(String[] args) {
		try {
			
			/*
			 * 要求用户输入用户名，密码，昵称
			 * 将该用户存入userinfo表
			 * 账户余额默认都是5000
			 */
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入用户名：");
			String username = scanner.nextLine();
			System.out.println("请输入密码：");
			String password = scanner.nextLine();
			System.out.println("请输入昵称：");
			String nickname = scanner.nextLine();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.0.113:1521:xe",
					"system",
					"123456");
			
			System.out.println("已经建立连接");
			
			Statement state = conn.createStatement();
			
			/*
			 * 向表中插入数据
			 * INSERT INTO userinfo_huo
			 * (id,username,paddword,nickname,account)
			 * VALUES
			 * (seq_userinfo_id_huo.NEXTVAL,'','','',5000)
			 */
			
			String sql = "INSERT INTO userinfo_huo "+
					"(id,username,password,nickname,account) "+
					"VALUES "+
					"(userinfo_id.NEXTVAL,'"+username+"','"+password+"','"+nickname+"',5000) ";
			
			
			int d = state.executeUpdate(sql);
			if(d > 0){
				System.out.println("插入"+d+"条记录");
			}
		
			conn.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
