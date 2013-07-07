package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.helpers.SQLTypeConverter;
import ro.visualDB.sql.query.SQLElement;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;

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
	
	public String toString() {
		return tableName;
	}
	
	public Element createDomElement(Document doc) throws Exception {
		Element el;
		el = doc.createElement("table");
		el.setAttribute("tableCatalogName", getTableCatalogName());
		el.setAttribute("tableSchemaName", getTableSchemaName());
		el.setAttribute("tableName", getTableName());
		el.setAttribute("tableType", getTableType());
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
    	newTbl.setTableCatalogName(atts.getValue("tableCatalogName"));
    	newTbl.setTableSchemaName(atts.getValue("tableSchemaName"));
    	newTbl.setTableName(atts.getValue("tableName"));
    	newTbl.setTableType(atts.getValue("tableType"));
		return newTbl;
	}
	
	@Override
	public String getCreateSqlStatement(int sqlEngine) throws Exception {
		String sql = null;
		switch (sqlEngine) {
			case SQLEngine.MYSQL:
				if (getChildrenCount() > 0) {
					sql = "CREATE TABLE IF NOT EXISTS " + tableName;
					sql += " ( \n";
					for (int i = 0 ; i < getChildrenCount(); i++) { 
						Column col = (Column)getChildAt(i);
						sql += "\t\t";
						String dataType = SQLTypeConverter.convertType(col.getDataType(), ((Remote)getTopParent()).getDatabaseEngine(), SQLEngine.MYSQL);
						sql += col.getColumnName() + " " + dataType;
						if (dataType.equalsIgnoreCase("NUMERIC") &&
								col.getNumericPrecision() != 0 &&
								col.getNumericScale() != 0) {
							sql += "(" + col.getNumericPrecision() + "," +
									col.getNumericScale() + ") ";
						} else if (dataType.equalsIgnoreCase("NUMERIC") &&
								col.getNumericPrecision() != 0) {
							sql += "(" + col.getNumericPrecision() + ") ";
						} else if ((dataType.equalsIgnoreCase("CHAR") || dataType.equalsIgnoreCase("VARCHAR")) &&
								col.getCharacterMaximumLength() != 0) {
							sql += "(" + col.getCharacterMaximumLength() + ") ";
						}
						if (col.getColumnDefault() != null ) {
							sql += " DEFAULT '" + col.getColumnDefault() + "'";
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
						String dataType = SQLTypeConverter.convertType(col.getDataType(),((Remote)getTopParent()).getDatabaseEngine(), SQLEngine.POSTGRES);
						sql += col.getColumnName() + " " + dataType;
						if (dataType.equalsIgnoreCase("NUMERIC") &&
								col.getNumericPrecision() != 0 &&
								col.getNumericScale() != 0) {
							sql += "(" + col.getNumericPrecision() + "," +
									col.getNumericScale() + ") ";
						} else if (dataType.equalsIgnoreCase("NUMERIC") &&
								col.getNumericPrecision() != 0) {
							sql += "(" + col.getNumericPrecision() + ") ";
						} else if ((dataType.equalsIgnoreCase("CHAR") || dataType.equalsIgnoreCase("VARCHAR")) &&
								col.getCharacterMaximumLength() != 0) {
							sql += "(" + col.getCharacterMaximumLength() + ") ";
						}
						if (col.getColumnDefault() != null ) {
							sql += " DEFAULT '" + col.getColumnDefault() + "'";
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
    	String sql = "";
    	boolean modified = false;    	
		switch (sqlEngine) {
			case SQLEngine.MYSQL:
				if (getChildrenCount() > 0) {
					sql = "ALTER TABLE " + tableCatalogName + "." + tableName + "\n";
					for (int i = 0 ; i < getChildrenCount(); i++) {
						Column col = (Column)getChildAt(i);
						if (col.isAltered()) {
							if (modified) {
								sql += ",\n";
							}
							modified = true;
							sql += "\t\tMODIFY COLUMN ";
							String dataType = SQLTypeConverter.convertType(col.getDataType(),((Remote)getTopParent()).getDatabaseEngine(), SQLEngine.POSTGRES);
							sql += col.getColumnName() + " " + dataType;
							if (dataType.equalsIgnoreCase("NUMERIC") &&
									col.getNumericPrecision() != 0 &&
									col.getNumericScale() != 0) {
								sql += "(" + col.getNumericPrecision() + "," +
										col.getNumericScale() + ") ";
							} else if (dataType.equalsIgnoreCase("NUMERIC") &&
									col.getNumericPrecision() != 0) {
								sql += "(" + col.getNumericPrecision() + ") ";
							} else if ((dataType.equalsIgnoreCase("CHAR") || dataType.equalsIgnoreCase("VARCHAR")) &&
									col.getCharacterMaximumLength() != 0) {
								sql += "(" + col.getCharacterMaximumLength() + ") ";
							}
							if (col.getColumnDefault() != null ) {
								sql += " DEFAULT '" + col.getColumnDefault() + "'";
							}
						}
					}
				}
				break;
			case SQLEngine.POSTGRES:
				if (getChildrenCount() > 0) {
					sql = "ALTER TABLE " + tableSchemaName + "." + tableName + "\n";
					for (int i = 0 ; i < getChildrenCount(); i++) {
						Column col = (Column)getChildAt(i);
						if (col.isAltered()) {
							if (modified) {
								sql += ",\n";
							}
							modified = true;
							sql += "\t\tALTER COLUMN ";
							String dataType = SQLTypeConverter.convertType(col.getDataType(),((Remote)getTopParent()).getDatabaseEngine(), SQLEngine.POSTGRES);
							sql += col.getColumnName() + " SET DATA TYPE " + dataType;
							if (dataType.equalsIgnoreCase("NUMERIC") &&
									col.getNumericPrecision() != 0 &&
									col.getNumericScale() != 0) {
								sql += "(" + col.getNumericPrecision() + "," +
										col.getNumericScale() + ") ";
							} else if (dataType.equalsIgnoreCase("NUMERIC") &&
									col.getNumericPrecision() != 0) {
								sql += "(" + col.getNumericPrecision() + ") ";
							} else if ((dataType.equalsIgnoreCase("CHAR") ||
										dataType.equalsIgnoreCase("VARCHAR") ||
										dataType.equalsIgnoreCase("CHARACTER") ||
										dataType.equalsIgnoreCase("CHARACTER VARYING")) &&
									col.getCharacterMaximumLength() != 0) {
								sql += "(" + col.getCharacterMaximumLength() + ") ";
							}
							if (col.getColumnDefault() != null ) {
								sql += " DEFAULT '" + col.getColumnDefault() + "'";
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
			return null;
	}
    
    
   	public String getVersioningSqlStatement(int sqlEngine) throws Exception {
       	String sql = "";
       	boolean modified = false;    	
   		switch (sqlEngine) {
   			case SQLEngine.MYSQL:
   				if (isAdded())
   					sql = getCreateSqlStatement(sqlEngine);
   				else if (isDirty())
   					sql = getAlterSqlStatement(sqlEngine);
   				else
   					sql = "";
   				break;
   			case SQLEngine.POSTGRES:
   				if (isAdded())
   					sql = getCreateSqlStatement(sqlEngine);
   				else if (isDirty())
   					sql = getAlterSqlStatement(sqlEngine);
   				else
   					sql = "";
   				break;
   			default:
   				break;
   		}
   		
   		return sql;
   	}
    @Override
    public boolean equalsName(TreeNode tn) {
    	if (tn instanceof Table) {
			return ((Table)tn).getTableName().equals(getTableName());
		} else {
			return false;
		}
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof Table) {
    		Table tn = (Table)obj;
    		return tableType.equals(tn.tableType);
    	} else {
    		return false;
    	}
    }
}
