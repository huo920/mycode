package day02;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import day01.DBUtil;

/**
 * 预编译SQL语句
 * 
 * statement适合执行静态SQL语句，即：SQL语句中没有拼接动态数据
 * 
 * PreparedStatement适合执行动态SQL
 * 
 * 数据库在收到SQL语句时，会理解该SQL语句并生成一个对应的执行计划（生成执行计划开销大）
 * 但是若重复发送同样SQL语句时，数据库会重用生成的执行计划，但是只要SQL语句中有动态数据，
 * 哪怕语义一致，数据不同，也不会重用执行计划，每次都会生成新的执行计划，为此：
 * 当含有动态数据但是语义相同的SQL要被执行时应当使用预编译SQl
 * @author tedu
 *
 */
public class PreparedStatementDemo {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入用户名：");
		String username = scan.nextLine();
		System.out.println("请输入密码：");
		String password = scan.nextLine();
		Connection conn = null;
		
		try {
			conn = DBUtil.getConnection();
			/*
			 * 预编译SQL语句中的动态数据可以用"?"代替
			 */
			
			String sql = "SELECT id,username,password,nickname,account "
					+ "FROM userinfo_huo "
					+ "WHERE username=? "
					+ "AND password=? ";
			/*
			 * PreparedStatement在创建的时候就需要将预编译的SQL语句传入，并发送给数据库先声称对应的执行计划
			 */
			PreparedStatement ps = conn.prepareStatement(sql);
			
			//设置？对应的值
			ps.setString(1, username);
			/*
			 * 哪怕密码中包含SQL敏感字符（SQL注入攻击）
			 * 也只会将其当做密码看待，而不会再改变SQL语义
			 */
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				System.out.println("登录成功");
			}else {
				System.out.println("账号或密码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
	}

}
