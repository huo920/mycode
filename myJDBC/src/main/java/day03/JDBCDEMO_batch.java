package day03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import day01.DBUtil;

/**
 * 批操作
 * 批操作是可以将要执行的大量SQL语句缓存在本地然后一次性发送给数据库，
 * 减少网络调用，调高执行效率
 * @author tedu
 *
 */
public class JDBCDEMO_batch {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			
//			Statement state = conn.createStatement();
//			for(int i =0 ;i<100;i++){
//				
//				String sql = "INSERT INTO userinfo_huo "
//						+"(id,username,password,nickname,account)"
//						+"VALUES "
//						+"(userinfo_id.NEXTVAL,'test+"+i+"','123123','test',5000)";
//				//每次调用excuteUpdate()都会发送给数据库
////				state.executeUpdate(sql);
//				state.addBatch(sql);  //先缓存到批中
//			}
//			
//			int[] data = state.executeBatch();
			
			
			/*
			 * 这种方式效率最高，使用更加方便
			 */
			conn.setAutoCommit(false);			
			String sql = "INSERT INTO userinfo_huo "
					+"(id,username,password,nickname,account) "
					+"VALUES "
					+"(userinfo_id.NEXTVAL,?,'123123','test',5000)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			int count = 0;
			for (int i = 0; i < 200; i++) {
				ps.setString(1, "test" + i);
				// ps.executeUpdate();
				ps.addBatch();
				count++;
				if (count % 50 == 0) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			
			int[] date = ps.executeBatch();
			conn.commit();
			
			System.out.println("执行完毕！");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeConnection(conn);
		}
		
	}
}


