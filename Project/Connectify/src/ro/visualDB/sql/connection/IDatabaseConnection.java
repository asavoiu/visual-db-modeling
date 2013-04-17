package ro.visualDB.sql.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnection {
	public Connection getConnection() throws SQLException;
}
