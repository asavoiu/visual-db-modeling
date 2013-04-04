package ro.visualDB.initiate.connections;

/**
 * Class for
 */

import ro.visualDB.initiate._types._TableType;
import ro.visualDB.initiate.tools.Table;
import ro.visualDB.xml.Tree;
import ro.visualDB.xml.TreeNode;

import java.sql.*;

public class PostgreSQLDatabaseConnection implements IDatabaseConnection {
	Connection conn = null;

    private String database;
	private String host;
	private String user;
	private String password;
	private String port;

	public PostgreSQLDatabaseConnection() throws Exception{
		throw new Exception("No Credentials");
	}

	public PostgreSQLDatabaseConnection(String host, String user, String password, String port, String database) throws Exception {
		this.host = host;
		this.user = user;
		this.password = password;
		this.database = database;
        this.port = port;
	}
	
	private void connect() throws Exception {
        System.out.println("-----------Iniitiating connection to "+database+"-----------");


//          baza de pe Heroku.com
//          connection = DriverManager.getConnection("jdbc:postgresql://ec2-54-225-69-193.compute-1.amazonaws.com:5432/d67ok9eheqe9a1", "fpflivmxynqdma","Ol-0WAibcjv5nmqtowfwazb7tR");
            conn = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database, user, password);

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
        Getters and setters for private Strings
     */
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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
