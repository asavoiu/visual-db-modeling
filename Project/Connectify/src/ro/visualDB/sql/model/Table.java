package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.sql.query.SQLElement;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLElement;

public class Table extends TreeNode implements SQLElement {
	/* TABLE_CAT  => table catalog (may be null) */
	String tableCatalogName = null;
	/* TABLE_SCHEM  => table schema (may be null) */
	String tableSchemaName = null;
	/* TABLE_NAME String => table name */
	String tableName = null;
	/* TABLE_TYPE String => table type. Typical types are "TABLE",
	 * "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY",
	 * "LOCAL TEMPORARY", "ALIAS", "SYNONYM".*/
	String tableType = null;
	/* REMARKS String => explanatory comment on the table */
	String remarks = null;
	/* TYPE_CAT String => the types catalog (may be null) */
	String typesCatalog = null;
	/* TYPE_SCHEM String => the types schema (may be null) */
	String typesSchema = null;
	/* TYPE_NAME String => type name (may be null) */
	String typeName = null;
	/* SELF_REFERENCING_COL_NAME String => name of the
	 * designated "identifier" column of a typed table (may be null)*/
	String selfRefrencingColName = null;
	/* REF_GENERATION String => specifies how values in
	 * SELF_REFERENCING_COL_NAME are created. Values are
	 * "SYSTEM", "USER", "DERIVED". (may be null)*/
	String refGeneration = null;
	
	public String getTableCatalogName() {
		return tableCatalogName;
	}
	public void setTableCatalogName(String tableCatalogName) {
		this.tableCatalogName = tableCatalogName;
	}
	public String getTableSchemaName() {
		return tableSchemaName;
	}
	public void setTableSchemaName(String tableSchemaName) {
		this.tableSchemaName = tableSchemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTypesCatalog() {
		return typesCatalog;
	}
	public void setTypesCatalog(String typesCatalog) {
		this.typesCatalog = typesCatalog;
	}
	public String getTypesSchema() {
		return typesSchema;
	}
	public void setTypesSchema(String typesSchema) {
		this.typesSchema = typesSchema;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSelfRefrencingColName() {
		return selfRefrencingColName;
	}
	public void setSelfRefrencingColName(String selfRefrencingColName) {
		this.selfRefrencingColName = selfRefrencingColName;
	}
	public String getRefGeneration() {
		return refGeneration;
	}
	public void setRefGeneration(String refGeneration) {
		this.refGeneration = refGeneration;
	}
	
	public String toString() {
		return tableName;
	}
	
	public Element createDomElement(Document doc) throws Exception {
		Element el;
		el = doc.createElement("table");
		el.setAttribute("name", getTableName());
		el.setAttribute("type", getTableType());
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
    	Table newTbl = new Table();
    	newTbl.setTableName(atts.getValue("name"));
    	newTbl.setTableType(atts.getValue("type"));
		return newTbl;
	}
	
	@Override
	public String getSqlStatement(int sqlEngine) throws Exception {
		String sql = "";
		switch (sqlEngine) {
			case SQLEngine.MYSQL:
				if (getChildrenCount() > 0) {
					sql = "CREATE TABLE " + tableName;
					sql += " ( \n";
					for (int i = 0 ; i < getChildrenCount(); i++) { 
						Column col = (Column)getChildAt(i);
						sql += "\t\t";
						sql += col.getColumnName() + " " + col.getTypeName();
						//TODO HACK replace this
						if (col.getTypeName().equalsIgnoreCase("BIT") ||
								col.getTypeName().equalsIgnoreCase("TINYINT") ||
								col.getTypeName().equalsIgnoreCase("SMALLINT") ||
								col.getTypeName().equalsIgnoreCase("MEDIUMINT") ||
								col.getTypeName().equalsIgnoreCase("INT") ||
								col.getTypeName().equalsIgnoreCase("INTEGER") ||
								col.getTypeName().equalsIgnoreCase("BIGINT") ||
								col.getTypeName().equalsIgnoreCase("REAL") ||
								col.getTypeName().equalsIgnoreCase("DOUBLE") ||
								col.getTypeName().equalsIgnoreCase("FLOAT") ||
								col.getTypeName().equalsIgnoreCase("DECIMAL") ||
								col.getTypeName().equalsIgnoreCase("NUMERIC") ||
								col.getTypeName().equalsIgnoreCase("CHAR") ||
								col.getTypeName().equalsIgnoreCase("VARCHAR") ||
								col.getTypeName().equalsIgnoreCase("BINARY") ||
								col.getTypeName().equalsIgnoreCase("VARBINARY")
								) {
							sql += "(" + col.getColumnSize() + ") ";
						}
						if (i < getChildrenCount() - 1) {
							sql += ",\n";
						}
					}
					sql += " );";
				}
				break;
			case SQLEngine.POSTGRES:
				if (getChildrenCount() > 0) {
					sql = "CREATE TABLE " + tableName;
					sql += " ( \n";
					for (int i = 0 ; i < getChildrenCount(); i++) { 
						Column col = (Column)getChildAt(i);
						sql += "\t\t";
						sql += col.getColumnName() + " " + col.getTypeName();
						//TODO HACK replace this
						if (col.getTypeName().equalsIgnoreCase("VARCHAR") ||
								col.getTypeName().equalsIgnoreCase("INT")) {
							sql += "(" + col.getColumnSize() + ") ";
						}
						if (i < getChildrenCount() - 1) {
							sql += ",\n";
						}
					}
					sql += " );";
				}
			default:
				break;
		}
		return sql;
	}
	
    @Override
	public String getModifySqlStatement(int sqlEngine) throws Exception {
    	String sql = "ALTER TABLE " + tableName + "\n";
    	boolean modified = false;    	
		switch (sqlEngine) {
			case SQLEngine.MYSQL:
				if (getChildrenCount() > 0) {
					for (int i = 0 ; i < getChildrenCount(); i++) {
						Column col = (Column)getChildAt(i);
						if (col.isAltered()) {
							if (modified) {
								sql += ",\n";
							}
							modified = true;
							sql += "\t\tMODIFY COLUMN ";
							sql += col.getColumnName() + " " + col.getTypeName();
							//TODO HACK replace this
							if (col.getTypeName().equalsIgnoreCase("BIT") ||
									col.getTypeName().equalsIgnoreCase("TINYINT") ||
									col.getTypeName().equalsIgnoreCase("SMALLINT") ||
									col.getTypeName().equalsIgnoreCase("MEDIUMINT") ||
									col.getTypeName().equalsIgnoreCase("INT") ||
									col.getTypeName().equalsIgnoreCase("INTEGER") ||
									col.getTypeName().equalsIgnoreCase("BIGINT") ||
									col.getTypeName().equalsIgnoreCase("REAL") ||
									col.getTypeName().equalsIgnoreCase("DOUBLE") ||
									col.getTypeName().equalsIgnoreCase("FLOAT") ||
									col.getTypeName().equalsIgnoreCase("DECIMAL") ||
									col.getTypeName().equalsIgnoreCase("NUMERIC") ||
									col.getTypeName().equalsIgnoreCase("CHAR") ||
									col.getTypeName().equalsIgnoreCase("VARCHAR") ||
									col.getTypeName().equalsIgnoreCase("BINARY") ||
									col.getTypeName().equalsIgnoreCase("VARBINARY")
									) {
								sql += "(" + col.getColumnSize() + ") ";
							}
						}
					}
				}
				break;
			case SQLEngine.POSTGRES:
				if (getChildrenCount() > 0) {
					for (int i = 0 ; i < getChildrenCount(); i++) {
						Column col = (Column)getChildAt(i);
						if (col.isAltered()) {
							if (modified) {
								sql += ",\n";
							}
							modified = true;
							sql += "\t\tALTER COLUMN ";
							sql += col.getColumnName() + " SET DATA TYPE " + col.getTypeName();
							//TODO HACK replace this
							if (col.getTypeName().equalsIgnoreCase("VARCHAR") ||
									col.getTypeName().equalsIgnoreCase("INT")) {
								sql += "(" + col.getColumnSize() + ") ";
							}
						}
					}
				}
				break;
			default:
				break;
		}
		if (modified)
			return sql;
		else 
			return "";
	}
}
