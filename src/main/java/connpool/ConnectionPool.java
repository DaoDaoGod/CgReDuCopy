package connpool;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
public class ConnectionPool {
	private static List<Connection> freeConnections = new ArrayList<Connection>();
	public static Connection getConnection() {
		if (freeConnections.isEmpty()) {
			Connection conn = ConnectionFactory.crateNewConnection();
			return conn;
		} else {
			Connection conn = freeConnections.get(0);
			freeConnections.remove(0);
			return conn;
		}
	}
	
	public static int returnConnection(Connection conn) {
		freeConnections.add(conn);
		return 0;
	}
	public static int connectionSum()
	{
		return  freeConnections.size();
	}


}
