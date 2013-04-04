package ro.visualDB.initiate.connections;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnectionFactory {
	
	public static Connection getConnection(String url, String user, String password) throws Exception {
		// The newInstance() call is a work around for some
		// broken Java implementations
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		return DriverManager.getConnection(url, user, password);
	}
}
