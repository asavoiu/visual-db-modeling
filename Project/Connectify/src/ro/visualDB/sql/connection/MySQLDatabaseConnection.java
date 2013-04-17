package ro.visualDB.sql.connection;

/**
 * Class for
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDatabaseConnection implements IDatabaseConnection {
	Connection conn = null;

	private String host = null;
	private String user = null;
	private String password = null;
	private String port = null;
    private String url = null;

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
	public Connection getConnection() throws SQLException {
		if (conn == null) {
			throw new SQLException("Null Connection");
		} else if (conn.isClosed()) {
			throw new SQLException("Closed Connection");

		}
		return conn;
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
	
	public String toString() {
		return host;
	}
}
