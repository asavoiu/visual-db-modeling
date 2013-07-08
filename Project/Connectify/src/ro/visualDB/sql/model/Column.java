package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLElement;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Column extends TreeNode {
	private String tableCatalogName;
    private String tableSchemaName;
    private String tableName;
    private String columnName;
    private int ordinalPosition;
    private String columnDefault;
    private String isNullable;
    private String dataType;
    private long characterMaximumLength;
    private long characterOctetLength;
    private long numericPrecision;
    private long numericScale;
    
    private Constraint constraint;


    /**
	 * TABLE_CAT String => table catalog (may be null)
	 */
	public String getTableCatalogName() {
		return tableCatalogName;
	}
	public void setTableCatalogName(String tableCatalogName) {
		this.tableCatalogName = tableCatalogName;
	}
	/**
	 * TABLE_SCHEM String => table schema (may be null)
	 */
	public String getTableSchemaName() {
		return tableSchemaName;
	}
	public void setTableSchemaName(String tableSchemaName) {
		this.tableSchemaName = tableSchemaName;
	}
	/**
	 * TABLE_NAME String => table name
	 */
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * COLUMN_NAME String => column name
	 */
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 *  DATA_TYPE int => SQL type from java.sql.Types
	 */
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * CHAR_OCTET_LENGTH int => for char types
	 * the maximum number of bytes in the column 
	 */
	public long getCharacterOctetLength() {
		return characterOctetLength;
	}
	public void setCharacterOctetLength(long charOctetLength) {
		this.characterOctetLength = charOctetLength;
	}
	/**
	 * ORDINAL_POSITION int => index of column in table (starting at 1)
	 */
	public int getOrdinalPosition() {
		return ordinalPosition;
	}
	public void setOrdinalPosition(int ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	/**
	 * IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
	 * YES --- if the column can include NULLs 
	 * NO --- if the column cannot include NULLs 
	 * empty string --- if the nullability for the column is unknown 
	 */
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	

	public Element createDomElement(Document doc) throws Exception {
		Element el;
        HashMap<Integer,String> dataTypes = new DataTypeValues().getDataTypes();
		el = doc.createElement("column");
		el.setAttribute("tableCatalogName", getTableCatalogName());
		el.setAttribute("tableSchemaName", getTableSchemaName());
		el.setAttribute("tableName", getTableName());
		el.setAttribute("columnName", getColumnName());
		el.setAttribute("ordinalPosition", getOrdinalPosition() + "");
		el.setAttribute("columnDefault", getColumnDefault());
		el.setAttribute("isNullable", getIsNullable());
		el.setAttribute("dataType", getDataType());
		el.setAttribute("characterMaximumLength", getCharacterMaximumLength() + "");
		el.setAttribute("characterOctetLength", getCharacterOctetLength() + "");
		el.setAttribute("numericPrecision", getNumericPrecision() + "");
		el.setAttribute("numericScale", getNumericScale() + "");
	    
        if(constraint != null){
            el.setAttribute("constraintCatalog", constraint.getConstraintCatalog());
            el.setAttribute("constraintSchema", constraint.getConstraintSchema());
            el.setAttribute("constraintName", constraint.getConstraintName());
            el.setAttribute("constraintTableCatalog", constraint.getTableCatalog());
            el.setAttribute("constraintTableSchema", constraint.getTableSchema());
            el.setAttribute("constraintTableName", constraint.getTableName());
            el.setAttribute("constraintColumnName", constraint.getColumnName());
            el.setAttribute("constraintType", constraint.getConstraintType());
        }

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
		Column newCol = new Column();
		newCol.setTableCatalogName(atts.getValue("tableCatalogName"));
		newCol.setTableSchemaName(atts.getValue("tableSchemaName"));
		newCol.setTableName(atts.getValue("tableName"));
		newCol.setColumnName(atts.getValue("columnName"));
		newCol.setOrdinalPosition(Integer.parseInt(atts.getValue("ordinalPosition")));
		newCol.setColumnDefault(atts.getValue("columnDefault"));
		newCol.setIsNullable(atts.getValue("isNullable"));
		newCol.setDataType(atts.getValue("dataType"));
		newCol.setCharacterMaximumLength(Long.parseLong(atts.getValue("characterMaximumLength")));
		newCol.setCharacterOctetLength(Long.parseLong(atts.getValue("characterOctetLength")));
		newCol.setNumericPrecision(Long.parseLong(atts.getValue("numericPrecision")));
		newCol.setNumericScale(Long.parseLong(atts.getValue("numericScale")));

		if (atts.getValue("constraintColumnName") != null) {
			Constraint c = new Constraint();
			c.setConstraintCatalog(atts.getValue("constraintCatalog"));
			c.setConstraintSchema(atts.getValue("constraintSchema"));
			c.setConstraintName(atts.getValue("constraintName"));
			c.setTableCatalog(atts.getValue("constraintTableCatalog"));
			c.setTableSchema(atts.getValue("constraintTableSchema"));
			c.setTableName(atts.getValue("constraintTableName"));
			c.setColumnName(atts.getValue("constraintColumnName"));
			c.setConstraintType(atts.getValue("constraintType"));
		}
		
		return newCol;
    }
    
    @Override
    public boolean equalsName(TreeNode tn) {
    	if (tn instanceof Column) {
			return ((Column)tn).getColumnName().equals(getColumnName());
		} else {
			return false;
		}
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof Column) {
    		Column tn = (Column)obj;
    		if (tableCatalogName.equals(tn.getTableCatalogName()) &&
				tableSchemaName.equals(tn.getTableSchemaName()) &&
				tableName.equals(tn.getTableName()) &&
				ordinalPosition == tn.getOrdinalPosition() &&
				columnDefault.equals(tn.getColumnDefault()) &&
				isNullable.equals(tn.getIsNullable()) &&
				dataType.equals(tn.getDataType()) &&
				characterMaximumLength == tn.getCharacterMaximumLength() &&
				characterOctetLength == tn.getCharacterOctetLength() &&
				numericPrecision == tn.getNumericPrecision() &&
				numericPrecision == tn.getNumericPrecision() &&
    			((constraint != null && tn.getConstraint() != null && constraint.equals(tn.getConstraint())) ||
    				(constraint == null && tn.getConstraint() == null))
    			) {
    			return true;
    		}
    		return false;
    	} else {
    		return false;
    	}
    }
	public long getNumericPrecision() {
		return numericPrecision;
	}
	public void setNumericPrecision(long numericPrecision) {
		this.numericPrecision = numericPrecision;
	}
	public long getNumericScale() {
		return numericScale;
	}
	public void setNumericScale(long numericScale) {
		this.numericScale = numericScale;
	}
	
	/**
	 * COLUMN_DEF String => default value for the column,
	 * which should be interpreted as a string when the
	 * value is enclosed in single quotes (may be null) 
	 */
	public String getColumnDefault() {
		return columnDefault;
	}
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	public long getCharacterMaximumLength() {
		return characterMaximumLength;
	}
	public void setCharacterMaximumLength(long characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}
	public Constraint getConstraint() {
		return constraint;
	}
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
	@Override
	public String getCreateSqlStatement(int sqlEngine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getAlterSqlStatement(int sqlEngine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getChangeSqlStatement(int sqlEngine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getModifySqlStatement(int sqlEngine) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return columnName;
	}
}
