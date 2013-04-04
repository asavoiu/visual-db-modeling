package ro.visualDB.initiate.connections;

import ro.visualDB.xml.Tree;

import java.sql.Connection;

public interface IDatabaseConnection {
	public Connection getConnection();
    public Tree getSchema() throws Exception;
    public Tree getSchema(String database) throws Exception;
}
