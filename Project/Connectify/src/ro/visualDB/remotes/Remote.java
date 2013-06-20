package ro.visualDB.remotes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.sql.query.SQLElement;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLElement;

public class Remote extends TreeNode {
	private String name;
	private String host;
	private String port;
	private String user;
	private String password;
	private String database;
	
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
		return rmt;
	}
	
	@Override
	public String getSqlStatement(int sqlEngine) throws Exception {
		String sql = "";
		for (TreeNode t : getChildren()) {
			sql += t.getSqlStatement(sqlEngine);
			sql += "\n";
		}
		return sql;
	}
	
}
