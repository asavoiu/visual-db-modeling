package ro.visualDB.sql.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ro.visualDB.xml.XMLElement;

import java.util.HashMap;
import java.util.Map;

public class Column implements XMLElement {
	private String tableCatalogName;

    private String tableSchemaName;
    private String tableName;
    private String columnName;
    private String dataType;
    private String typeName;
    private int columnSize;
    private int bufferLength;
    private int decimalDigits;
    private int numPrecRadix;
    private int nullable;
    private String remarks;
    private String columnDef;
    private int sqlDataType;
    private int sqlDateTimeSub;
    private int charOctetLength;
    private int ordinalPosition;
    private String isNullable;
    private String scopeCatalog;
    private String scopeSchema;
    private String scopeTable;
    private short sourceDataType;
    private String isAutoIncrement;
    private String isGeneratedColumn;
    private boolean primaryKey;
    private boolean foreignKey;
    private ForeignKeys foreignKeys;


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
	 *  TYPE_NAME String => Data source dependent type name,
	 *   for a UDT the type name is fully qualified
	 */
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * COLUMN_SIZE int => column size.
	 */
	public int getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}
	/** 
	 * BUFFER_LENGTH is not used.
	 */
	public int getBufferLength() {
		return bufferLength;
	}
	public void setBufferLength(int bufferLength) {
		this.bufferLength = bufferLength;
	}
	/** DECIMAL_DIGITS int => the number of fractional digits.
	 *  Null is returned for data types where DECIMAL_DIGITS is not applicable. 
	 */
	public int getDecimalDigits() {
		return decimalDigits;
	}
	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	/**
	 * NUM_PREC_RADIX int => Radix (typically either 10 or 2)
	 */
	public int getNumPrecRadix() {
		return numPrecRadix;
	}
	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}
	/** NULLABLE int => is NULL allowed.
	 * columnNoNulls - might not allow NULL values
	 * columnNullable - definitely allows NULL values 
	 * columnNullableUnknown - nullability unknown 
	 */
	public int getNullable() {
		return nullable;
	}
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}
	/**
	 *  REMARKS String => comment describing column (may be null)
	 */
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * COLUMN_DEF String => default value for the column,
	 * which should be interpreted as a string when the
	 * value is enclosed in single quotes (may be null) 
	 */
	public String getColumnDef() {
		return columnDef;
	}
	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}
	/**
	 * SQL_DATA_TYPE int => unused
	 */
	public int getSqlDataType() {
		return sqlDataType;
	}
	public void setSqlDataType(int sqlDataType) {
		this.sqlDataType = sqlDataType;
	}
	/**
	 * SQL_DATETIME_SUB int => unused
	 */
	public int getSqlDateTimeSub() {
		return sqlDateTimeSub;
	}
	public void setSqlDateTimeSub(int sqlDateTimeSub) {
		this.sqlDateTimeSub = sqlDateTimeSub;
	}
	/**
	 * CHAR_OCTET_LENGTH int => for char types
	 * the maximum number of bytes in the column 
	 */
	public int getCharOctetLength() {
		return charOctetLength;
	}
	public void setCharOctetLength(int charOctetLength) {
		this.charOctetLength = charOctetLength;
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
	/**
	 * SCOPE_CATALOG String => catalog of table that is the scope
	 * of a reference attribute (null if DATA_TYPE isn't REF)
	 */
	public String getScopeCatalog() {
		return scopeCatalog;
	}
	public void setScopeCatalog(String scopeCatalog) {
		this.scopeCatalog = scopeCatalog;
	}
	/**
	 * SCOPE_SCHEMA String => schema of table that is the scope
	 * of a reference attribute (null if the DATA_TYPE isn't REF)
	 */
	public String getScopeSchema() {
		return scopeSchema;
	}
	public void setScopeSchema(String scopeSchema) {
		this.scopeSchema = scopeSchema;
	}
	/** 
	 * SCOPE_TABLE String => table name that this the scope
	 * of a reference attribute (null if the DATA_TYPE isn't REF) 
	 */
	public String getScopeTable() {
		return scopeTable;
	}
	public void setScopeTable(String scopeTable) {
		this.scopeTable = scopeTable;
	}
	/** 
	 * SOURCE_DATA_TYPE short => source type of a distinct type
	 * or user-generated Ref type, SQL type from java.sql.Types
	 * (null if DATA_TYPE isn't DISTINCT or user-generated REF) 
	 */
	public short getSourceDataType() {
		return sourceDataType;
	}
	public void setSourceDataType(short sourceDataType) {
		this.sourceDataType = sourceDataType;
	}
	/**
	 * IS_AUTOINCREMENT String => Indicates whether this column is auto incremented 
	 * YES --- if the column is auto incremented 
	 * NO --- if the column is not auto incremented 
	 * empty string --- if it cannot be determined whether the column is auto incremented
	 */
	public String getIsAutoIncrement() {
		return isAutoIncrement;
	}
	public void setIsAutoIncrement(String isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}
	/** 
	 * IS_GENERATEDCOLUMN String => Indicates whether this is a generated column 
	 * YES --- if this a generated column 
	 * NO --- if this not a generated column 
	 * empty string --- if it cannot be determined whether this is a generated column
	 */
	public String getIsGeneratedColumn() {
		return isGeneratedColumn;
	}
	public void setIsGeneratedColumn(String isGeneratedColumn) {
		this.isGeneratedColumn = isGeneratedColumn;
	}

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
	
	public String toString() {
		return columnName;
	}

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public ForeignKeys getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ForeignKeys foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    @Override
	public Element getDomElement(Document doc) throws Exception {
		Element el;
        HashMap<Integer,String> dataTypes = new DataTypeValues().getDataTypes();
		el = doc.createElement("column");
		el.setAttribute("name", getColumnName());

        if(isForeignKey()){
            el.setAttribute("foreignKey", isForeignKey()+"");
            el.setAttribute("primaryKeySchemaName", getForeignKeys().getPrimaryKeySchemaName());
            el.setAttribute("primaryKeyTableName", getForeignKeys().getPrimaryKeyTableName());
            el.setAttribute("primaryKeyColumName", getForeignKeys().getPrimaryKeyColumnName());
        }

        if(isPrimaryKey()){
            el.setAttribute("primaryKey", "true");
        }
        el.setAttribute("dataType", dataTypes.get(Integer.valueOf(getDataType())));
        el.setAttribute("isAutoIncrement", getIsAutoIncrement());
		el.setAttribute("isNullable", getIsNullable());
		return el;
	}
}
