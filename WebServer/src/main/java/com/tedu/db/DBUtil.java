package com.tedu.db;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

/**
 * 数据库连接的管理类
 * @author adminitartor
 *
 */
public class DBUtil {
	private static BasicDataSource ds;

	static{
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("config.properties"));
			String driverclass = prop.getProperty("driverclass");
			String url = prop.getProperty("url");
			String username = prop.getProperty("username");
			String password = prop.getProperty("password");
			int maxActive = Integer.parseInt(
				prop.getProperty("maxactive")
			);
			int maxWait = Integer.parseInt(
				prop.getProperty("maxwait")	
			);
			
			System.out.println("初始化数据库连接...");
			System.out.println(driverclass);
			System.out.println(url);
			System.out.println(username);
			System.out.println(password);
			
			ds = new BasicDataSource();
			
			//Class.forName(...)
			ds.setDriverClassName(driverclass);
			
			//DriverManager.getConnection(...)
			ds.setUrl(url);
			ds.setUsername(username);
			ds.setPassword(password);
			
			//设置最大连接数
			ds.setMaxActive(maxActive);
			//设置最大等待时间
			ds.setMaxWait(maxWait);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取一个数据库连接
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static Connection getConnection() throws SQLException{
		try {
			/*
			 * 向连接池获取连接
			 * 若连接池中没有可用连接时，该方法会
			 * 阻塞当前线程，阻塞时间由连接池设置
			 * 的maxWait决定。当阻塞过程中连接池
			 * 有了可用连接时会立即将连接返回。若
			 * 超时仍然没有可用连接时，该方法会抛出
			 * 异常。
			 */
			return ds.getConnection();			
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public static void closeConnection(Connection conn){
		try {
			if(conn != null){
				conn.setAutoCommit(true);
				/*
				 * 连接池的连接对于close方法的
				 * 处理是将连接的在连接池中的状态设置为空闲
				 * 而非真的将其关闭。
				 */
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}





