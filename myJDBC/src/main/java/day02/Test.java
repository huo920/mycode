package day02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import day01.DBUtil;

/**
 * 转账功能 输入转出账号的用户名，在输入转入账号的用户名 最后输入要转账的金额完成转账操作
 * 
 * @author tedu
 *
 */
public class Test {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输出转出账号的用户名：");
		String usernameOut = scan.nextLine();
		System.out.println("请输入转出账号的用户名：");
		String usernameIn = scan.nextLine();
		System.out.println("请输入转出金额：");
		double money = Double.parseDouble(scan.nextLine());
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();
			/*
			 * JDBC默认是自动提交事务的，即：每当执行一条DML操作后都会提交事务
			 * 
			 * 若希望自行控制事务，需要将自动提交事务关闭
			 */
			conn.setAutoCommit(false);

			/*
			 * UPDATE userinfo_huo SET account=account-? WHERE username=?
			 */
			//转出操作
			String sqlOut = "UPDATE userinfo_huo " + "SET account=account-? " + "WHERE username=? ";
			PreparedStatement psOut = conn.prepareStatement(sqlOut);
			psOut.setDouble(1, money);
			psOut.setString(2, usernameOut);
			int d = psOut.executeUpdate();

			if (d > 0) {
				//转入操作
				String sqlIn = "UPDATE userinfo_huo " + "SET account=account+? " + "WHERE username=? ";
				PreparedStatement psIn = conn.prepareStatement(sqlIn);
				psIn.setDouble(1, money);
				psIn.setString(2, usernameIn);
				d = psIn.executeUpdate();

				if (d > 0) {
					System.out.println("转账成功");
					//提交事务
					conn.commit();
				} else {
					System.out.println("转入用户名错误");
					//回滚事务
					conn.rollback();
				}
			} else{
				System.out.println("转出用户名错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DBUtil.closeConnection(conn);
		}
	}
}
