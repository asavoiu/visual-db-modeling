package ro.visualDB.initiate.connections;

/**
 * Class for
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ro.visualDB.initiate._types._TableType;
import ro.visualDB.initiate.tools.Table;
import ro.visualDB.xml.Tree;
import ro.visualDB.xml.TreeNode;

public class MySQLDatabaseConnection implements IDatabaseConnection {
	Connection conn = null;

    private String database;
	private String url;
	private String user;
	private String password;
	private String port;

	public MySQLDatabaseConnection() throws Exception{
		throw new Exception("No Credentials");
	}
	
	public MySQLDatabaseConnection(String url, String user, String password, String port, String database) throws Exception {
		this.url = url;
		this.user = user;
		this.password = password;
		this.database = database;
		this.port = port;
	}
	
	private void connect() throws Exception {
        System.out.println("-----------Iniitiating connection to "+database+"-----------");
		conn = MySqlConnectionFactory.getConnection(url, user, password);
        System.out.println("-----------Connection successful-----------");
	}
	
	private void closeConnection() throws Exception {
		conn.close();
		conn = null;
	}
	
	@Override
	public Connection getConnection() {
		return conn;
	}
	
	public Tree getSchema() throws Exception {
		return getSchema(database);
	}
	
	public Tree getSchema(String database) throws Exception {
		connect();
		Tree tree = new Tree(new TreeNode(database));
		String query = "SELECT table_name, table_type "
				+ " FROM information_schema.tables"
				+ " WHERE table_schema = '" + database + "' "
				+ " ORDER BY table_name";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Table newTable = new Table(rs.getString(1), _TableType.getTableType(rs.getString(2)));
				tree.addNode(new TreeNode(newTable));
			}
			
			closeConnection();
			return tree;
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

    /**
     *  Getters and setters for private Strings
     */
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
