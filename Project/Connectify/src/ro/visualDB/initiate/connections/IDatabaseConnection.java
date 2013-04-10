package ro.visualDB.initiate.connections;

import ro.visualDB.xml.Tree;

import java.sql.Connection;

public interface IDatabaseConnection {
	public Connection connection = null;
	public Connection getConnection();
    public Tree getSchema(String database) throws Exception;
}
