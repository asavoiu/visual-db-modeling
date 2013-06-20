package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.sql.query.SQLElement;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLElement;

/**
 * Class describing SQL Catalog from a database.<br/>
 * <br/>
 * A rough explanation of catalogs and schema: <br/>
 * In Oracle:<br/>
 * - server instance == database == catalog == all data managed by same execution engine<br/>
 * - schema == namespace within database, identical to user account<br/>
 * - user == schema owner == named account, identical to schema,
 * who can connect to database, who owns the schema and use objects
 * possibly in other schemas to identify any object you need
 * (schema name + object name)<br/>
 * <br/>
 * In PostgreSQL:<br/>
 * - server instance == db cluster == all data managed by same execution engine<br/>
 * - database == catalog == single database within db cluster,
 * isolated from other databases in same db cluster<br/>
 * - schema == namespace within database<br/>
 * - user == named account, who can connect to database, own and use objects in database
 * to identify any object you need (database name + schema name + object name)<br/>
 * <br/>
 * In MySQL:<br/>
 * - server instance == not identified with catalog, just a set of databases<br/>
 * - database == catalog == a namespace within the server.<br/>
 * - schema == not used (empty in JDBC driver, not used in official docs).<br/>
 * - user == named account, who is can connect to server and use 
 * (but can not own - no concept of ownership) objects in one or more databases
 * to identify any object you need (database name + object name)
 * 
 * From official MySql Forum:
 * "Difference between Catalog and Schema" -> In MySQL, they are the same.
 * Also:
 * CREATE DATABASE has the synonym CREATE SCHEMA.
 */

public class Catalog extends TreeNode {
	private String catalogTerm;
	private String catalogName;

	/**
	 * Catalog name<br/>
	 * (TABLE_CAT String => catalog name) 
	 * */
	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	/**
	 * Retrieves the database vendor's preferred term for "catalog".
	 */
	public String getCatalogTerm() {
		return catalogTerm;
	}

	public void setCatalogTerm(String catalogTerm) {
		this.catalogTerm = catalogTerm;
	}
	
	public String toString() {
		return catalogName;
	}

	public Element createDomElement(Document doc) throws Exception {
		Element el;
		el = doc.createElement("catalog");
		el.setAttribute("name", getCatalogName());
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
		Catalog newCat = new Catalog();
		newCat.setCatalogName(atts.getValue("name"));
		return newCat;
	}

	@Override
	public String getSqlStatement(int sqlEngine) throws Exception {
		String sql = "";
		switch (sqlEngine) {
			case SQLEngine.MYSQL:
					sql =  "CREATE DATABASE " + catalogName + ";";
					break;
			case SQLEngine.POSTGRES:
					sql =  "CREATE DATABASE " + catalogName + ";";
					break;
			default:
					break;
		}
		sql += "\n";
		for (TreeNode t : getChildren()) {
			sql += t.getSqlStatement(sqlEngine);
			sql += "\n";
		}
		return sql;
	}
	
}
