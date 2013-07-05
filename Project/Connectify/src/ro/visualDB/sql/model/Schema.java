package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.sql.query.SQLElement;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLElement;

public class Schema extends TreeNode implements SQLElement {
	private String schemaTerm;
	private String schemaName;
	private String catalogName;
	
	/**
	 * Retrieves the database vendor's preferred term for "schema".
	 */
	public String getSchemaTerm() {
		return schemaTerm;
	}
	public void setSchemaTerm(String schemaTerm) {
		this.schemaTerm = schemaTerm;
	}
	/**
	 * Schema name 
	 */
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	/**
	 * Catalog name (may be null)
	 */
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	} 

	public String toString() {
		return schemaName;
	}

	public Element createDomElement(Document doc) throws Exception {
		Element el;
		el = doc.createElement("schema");
		el.setAttribute("name", getSchemaName());
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
    	Schema newSch = new Schema();
		newSch.setSchemaName(atts.getValue("name"));
		return newSch;
	}
	@Override
	public String getSqlStatement(int sqlEngine) throws Exception {
		String sql = "";
		switch (sqlEngine) {
			case SQLEngine.MYSQL:
				sql = "";
				break;
			case SQLEngine.POSTGRES:
				sql = "CREATE SCHEMA " + schemaName + ";\n";
				break;
			default:
				break;
		}
		for (TreeNode t : getChildren()) {
			sql += t.getSqlStatement(sqlEngine);
			sql += "\n";
		}
		return sql;
	}
	
	@Override
	public boolean equalsName(TreeNode tn) {
		if (tn instanceof Schema) {
			return ((Schema)tn).getSchemaName().equals(getSchemaName());
		} else {
			return false;
		}
	}
	
	//TODO ?? modify this
	@Override
	public boolean equals(Object tn) {
		if (tn instanceof Schema) {
			return ((Schema)tn).getSchemaName().equals(getSchemaName());
		} else {
			return false;
		}
	}
}
