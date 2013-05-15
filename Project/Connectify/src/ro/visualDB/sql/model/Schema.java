package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLElement;

public class Schema implements XMLElement{
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
	@Override
	public Element getDomElement(Document doc) throws Exception {
		Element el;
		el = doc.createElement("schema");
		el.setAttribute("name", getSchemaName());
		return el;
	}
	
	@Override
	public TreeNode parseElement(String uri, String localName, String qName,
			Attributes atts) {
    	TreeNode tn = new TreeNode();
    	Schema newSch = new Schema();
		newSch.setSchemaName(atts.getValue("name"));
		tn.setValue(newSch);
		return tn;
	}
}
