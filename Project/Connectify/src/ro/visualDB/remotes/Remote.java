package ro.visualDB.remotes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.connection.MySQLDatabaseConnection;
import ro.visualDB.sql.connection.PostgreSQLDatabaseConnection;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;

public class Remote extends TreeNode {
	private String name;
	private String host;
	private String port;
	private String user;
	private String password;
	private String database;
	private int databaseEngine;
	private boolean ssl = false;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public Element createDomElement(Document doc) throws Exception {
		Element el;
		el = doc.createElement("remote");
		el.setAttribute("name", getName());
		el.setAttribute("host", getHost());
		el.setAttribute("port", getPort());
		el.setAttribute("user", getUser());
		el.setAttribute("password", getPassword());
		el.setAttribute("database", getDatabase());
		el.setAttribute("databaseEngine", "" + getDatabaseEngine());
		el.setAttribute("ssl", "" + ssl);
		return el;
	}
	
	@Override
	public Element getDomElement(Document doc) throws Exception {
		Element el = createDomElement(doc);
		for (TreeNode t : getChildren()) {
			el.appendChild(t.getDomElement(doc));
		}
		return el;
	}
	
	@Override
	public TreeNode parseElement(String uri, String localName, String qName,
			Attributes atts) {
    	Remote rmt = new Remote();
    	rmt.setName(atts.getValue("name"));
    	rmt.setHost(atts.getValue("host"));
    	rmt.setPort(atts.getValue("port"));
    	rmt.setUser(atts.getValue("user"));
    	rmt.setPassword(atts.getValue("password"));
    	rmt.setDatabase(atts.getValue("database"));
    	rmt.setDatabaseEngine(Integer.parseInt(atts.getValue("databaseEngine")));
    	rmt.setSsl(Boolean.parseBoolean(atts.getValue("ssl")));

		return rmt;
	}
	
	@Override
	public String getCreateSqlStatement(int sqlEngine) throws Exception {
		return null;
	}
	
	@Override
	public String getModifySqlStatement(int sqlEngine) throws Exception {
		String sql = "";
		for (TreeNode t : getChildren()) {
			sql += t.getModifySqlStatement(sqlEngine);
			sql += "\n";
		}
		return sql;
	}
	
	public int getDatabaseEngine() {
		return databaseEngine;
	}
	
	public void setDatabaseEngine(int databaseEngine) {
		this.databaseEngine = databaseEngine;
	}
	
	public IDatabaseConnection getConnection() throws Exception {
		switch (databaseEngine) {
			case SQLEngine.MYSQL:
				return new MySQLDatabaseConnection( host,
													port,
													user,
													password,
													database);
			case SQLEngine.POSTGRES:
				return new PostgreSQLDatabaseConnection( host,
														 port,
														 user,
														 password,
														 database,
														 ssl);
			default:
				return null;
		}
	}
	public boolean isSsl() {
		return ssl;
	}
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}
}
