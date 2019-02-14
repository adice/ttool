package com.ttool.biz.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class DBUtil {

	private static BoneCP connectionPool = null;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 设置连接池配置信息
			BoneCPConfig config = new BoneCPConfig();
			// 数据库的JDBC URL
			config.setJdbcUrl("jdbc:mysql://localhost:3306/ttool_db?useUnicode=true&characterEncoding=UTF-8");
			// 数据库用户名
			config.setUsername("root");
			// 数据库用户密码
			config.setPassword("");
			// 数据库连接池的最小连接数
			config.setMinConnectionsPerPartition(5);
			// 数据库连接池的最大连接数
			config.setMaxConnectionsPerPartition(15);
			config.setPartitionCount(1);
			// 设置数据库连接池
			connectionPool = new BoneCP(config);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Connection getCon() {
		try {
			// 从数据库连接池获取一个数据库连接
			return connectionPool.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void closeCon(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void shutdownConnectionPool() {
		connectionPool.shutdown();
	}
}
