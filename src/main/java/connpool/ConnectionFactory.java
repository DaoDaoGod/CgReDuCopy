package connpool;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {
	public static Connection crateNewConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/myfilm", "root", "8608");
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
