package day01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * 创建序列 seq_userinfo_id_huo 从1开始,步进为1
 * 
 * @author tedu
 *
 */
public class JDBCDemo2 {
	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.0.113:1521:xe", 
					"system",
					"123456");

			System.out.println("已经建立连接");

			Statement state = conn.createStatement();

			/*
			 * CREATE SEQUENCE userinfo_id_huo START WITH 1 INCREMENT BY 1
			 */
			String sql = "CREATE SEQUENCE userinfo_id " + 
					" START WITH 1 " + 
					" INCREMENT BY 1 ";

			state.execute(sql);
			System.out.println("创建完毕");
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
