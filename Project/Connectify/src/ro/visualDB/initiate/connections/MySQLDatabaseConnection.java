package ro.visualDB.initiate.connections;

/**
 * Class for
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ro.visualDB.initiate._types._TableType;
import ro.visualDB.initiate.tools.Table;
import ro.visualDB.xml.Tree;
import ro.visualDB.xml.TreeNode;

public class MySQLDatabaseConnection implements IDatabaseConnection {
	Connection conn = null;

	private String host;
	private String user;
	private String password;
	private String port;
	private String url;

	public MySQLDatabaseConnection() throws Exception{
		throw new Exception("No Credentials");
	}
	
	public MySQLDatabaseConnection(String host,
			String port,
			String user,
			String password) throws Exception {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.url = "jdbc:mysql://"+ host + ":" + port + "/";
     	Class.forName("com.mysql.jdbc.Driver").newInstance();
     	this.conn = DriverManager.getConnection(url, user, password);
	}
	
	@Override
	public Connection getConnection() {
		return conn;
	}
	
	public Tree getSchema(String database) throws Exception {
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
		}
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
