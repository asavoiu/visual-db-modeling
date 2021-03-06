package ro.visualDB.sql.connection;

/**
 * Class for
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLDatabaseConnection implements IDatabaseConnection {

    private Connection conn;

    private String host;
    private String user;
    private String password;
    private String port;
    private String url;
    private String database;
    private Properties props;
    
    public PostgreSQLDatabaseConnection() throws Exception {
        throw new Exception("No Credentials");
    }

    public PostgreSQLDatabaseConnection(String host, String port, String user,
                                        String password, String database, boolean ssl) throws Exception {
        System.out.println("____________________Starting PostgreSQL connection for "+database+"____________________");
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
    	props = new Properties();
        props.setProperty("user", this.user);
        props.setProperty("password", this.password);
        if (ssl) {
            props.setProperty("ssl", "true");
            props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        }
        Class.forName("org.postgresql.Driver").newInstance();
    }

    @Override
    public Connection getConnection() throws SQLException {
    	if (conn == null) {
			conn = DriverManager.getConnection(url, props);
		} else if (conn.isClosed()) {
			conn = DriverManager.getConnection(url, props);
		} else if (!conn.isClosed()) {
			conn.close();
			conn = DriverManager.getConnection(url, props);
		}
		return conn;
    }
    
    @Override
    public Connection getConnection(String database) throws SQLException {
		String url = "jdbc:mysql://"+ host + ":" + port + "/" + database;
    	if (conn == null) {
			conn = DriverManager.getConnection(url, props);
		} else if (conn.isClosed()) {
			conn = DriverManager.getConnection(url, props);
		} else if (!conn.isClosed()) {
			conn.close();
			conn = DriverManager.getConnection(url, props);
		}
		return conn;
    }

    /**
     * Getters and setters for private Strings
     */
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String toString() {
        return host;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}