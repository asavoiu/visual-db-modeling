package ro.dbviz.sql;

import java.sql.Connection;

public interface IDatabaseConnection {
	public Connection getConnection();
}
