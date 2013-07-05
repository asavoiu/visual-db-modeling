package ro.visualDB.sql.helpers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.connection.IDatabaseConnection;

public class SQLQueryExecutorHelper {

	public static boolean executeQuery(Connection conn, String query) throws SQLException {
		Statement st = conn.createStatement();
		return st.execute(query);
	}
	
	public static boolean executeQuery(IDatabaseConnection conn, String query) throws SQLException {
		Statement st = conn.getConnection().createStatement();
		return st.execute(query);
	}
	
	public static boolean executeQueryOnRemote(Remote rmt, String query) throws Exception {
		return SQLQueryExecutorHelper.executeQuery(rmt.getConnection(), query);
	}
	
	public static boolean executeQueryOnDatabase(Remote rmt, String database, String query) throws Exception {
		return SQLQueryExecutorHelper.executeQuery(rmt.getConnection().getConnection(database), query);
	}
}
