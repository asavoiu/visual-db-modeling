package ro.visualDB.sql.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnection {
	public Connection getConnection() throws SQLException;
	public Connection getConnection(String database) throws SQLException;
    public String getHost();
    public void setHost(String host);
    public String getUser();
    public void setUser(String user);
    public String getPassword();
    public void setPassword(String password);
    public String getPort();
    public void setPort(String port);
    public String getUrl();
    public void setUrl(String url);
    public String toString();
    public String getDatabase();
    public void setDatabase(String database);
}