package day03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import day01.DBUtil;

/**
 * 返回自动主键
 * @author tedu
 *
 */
public class JDBCDemo2 {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			
			String sql = "INSERT INTO dept_huo "
					+"(deptno,dname,loc)"
					+"VALUES "
					+"(seq_dept_id_huo.NEXTVAL,?,?)";
			
			/*
			 * 创建ps的同时指定执行该ps对应的SQL语句后
			 * 要得到插入记录中指定字段的值
			 */
			PreparedStatement ps = conn.prepareStatement(sql,new String[]{"deptno"});
			ps.setString(1, "IT");
			ps.setString(2, "BeiJing");
			ps.executeUpdate();
			//获取插入的数据中指定字段的值
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			System.out.println("插入部门的ID是："+id);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
	}
}
