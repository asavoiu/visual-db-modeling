package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ro.visualDB.logging.AppLogger;
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
 */
public class Catalog implements XMLElement {
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

	//TODO Make proper XML element with tagName and attributes and shit
	@Override
	public Element getDomElement(Document doc) throws Exception {
		Element el;
		el = doc.createElement(catalogName);
		return el;
	}
}
