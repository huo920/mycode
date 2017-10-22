package day02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import day01.DBUtil;

/**
 * 对指定的用户修改密码
 * 输入对应的用户名，然后以及要修改的密码，
 * 将其修改
 * @author tedu
 *
 */

public class PreparedStatementDemo3 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入用户名：");
		String username = scan.nextLine();
		System.out.println("请修改密码：");
		String password = scan.nextLine();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			
			/*
			 * UPDATE userinfo_huo
			 * SET password=?
			 * WHERE username='"+username+"'
			 */
			String sql  = "UPDATE userinfo_huo "
					+"SET password=? "
					+"WHERE username=? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, username);
			int d = ps.executeUpdate();
			if(d>0){
				System.out.println("修改成功");
			}else{
				System.out.println("修改失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
	}

}
