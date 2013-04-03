package ro.dbviz.sql.mysqli;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ro.dbviz.sql.IDatabaseConnection;

public class DatabaseConnection implements IDatabaseConnection {
	Connection conn = null;
	private String database;
	private String url;
	private String user;
	private String password;
	
	public DatabaseConnection() throws Exception{
		throw new Exception("No Credentials");
	}
	
	public DatabaseConnection(String url, String user, String password, String database) throws Exception {
		this.url = url;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	private void connect() throws Exception {
		conn = MySqlConnectionFactory.getConnection(url, user, password);
	}
	
	private void closeConnection() throws Exception {
		conn.close();
		conn = null;
	}
	
	@Override
	public Connection getConnection() {
		return conn;
	}
	
	public void getSchema() throws Exception {
		connect();
		String query = "SELECT table_name, table_type, engine "
				+ " FROM information_schema.tables"
				+ " WHERE table_schema = '" + database + "' "
				+ " ORDER BY table_name";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString(1) +
						" - " + rs.getString(2) +
						" - " + rs.getString(3));
			}
			closeConnection();
			// do not catch exceptions
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
				rs = null;
		    }
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
					// ignore	
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					closeConnection();
				} catch (SQLException sqlEx) {
					// ignore	
				}
			}
		}
		
	}
}
