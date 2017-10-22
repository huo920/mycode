package day02;

import java.sql.Connection;
import java.sql.PreparedStatement;

import day01.DBUtil;

/**
 * 批量执行语义相同的SQL时，ps可以重用执行计划 减小数据库开销
 * 
 * @author tedu
 *
 */
public class PreparedStatementDemo2 {
	/*
	 * 批量象usernfo表中插入1000条数据 批量操作影响数据库执行效率主要三个方面： 1：网络传输 2：执行计划的生成 3：事务管理
	 */
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO userinfo_huo "
					+"(id,username,password,nickname,account) "
					+"VALUES "
					+"(userinfo_id.NEXTVAL,?,'123456',?,?)";
			System.out.println(sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			
			for(int i =0;i<1000;i++){
				
				ps.setString(1, "test"+i);
				ps.setString(2, "test"+i);
				ps.setInt(3, 5000);
				ps.executeUpdate();  //重用同一个执行计划1000次
			
			}
			conn.commit();
			System.out.println("插入完毕！");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
	}

}
