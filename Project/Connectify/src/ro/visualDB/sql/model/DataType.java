package ro.visualDB.sql.model;

/**
 * Class describing SQL Data Types available 
 * in a database.<br/>
 * Each data type has different attributes,
 * encapsulated in this class.
 * Data Types Example: Int, Bool, DateTime,
 * TimeStamp, Struct, Number, etc.
 */
public class DataType {
	private String typeName;
	private int dataType;
	private int precision;
	private String literalPrefix;
	private String literalSuffix;
	private String createParams;
	private short nullable;
	private boolean caseSensitive;
	private short searchable;
	private boolean unsignedAttribute;
	private boolean fixedPrecScale;
	private boolean autoIncrement; 
	private String localTypeName;
	private short minimumScale;
	private short maximumScale; 
	private int sqlDataType; 
	private int sqlDatetimeSub;
	private int numPrecRadix; 
	
	/**
	 * Type name
	 */
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * SQL data type from java.sql.Types
	 */
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	/**
	 * Maximum precision 
	 */
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	/**
	 * Prefix used to quote a literal (may be null) 
	 */
	public String getLiteralPrefix() {
		return literalPrefix;
	}
	public void setLiteralPrefix(String literalPrefix) {
		this.literalPrefix = literalPrefix;
	}
	/**
	 * Suffix used to quote a literal (may be null) 
	 */
	public String getLiteralSuffix() {
		return literalSuffix;
	}
	public void setLiteralSuffix(String literalSuffix) {
		this.literalSuffix = literalSuffix;
	}
	/**
	 * Parameters used in creating the type (may be null) 
	 */
	public String getCreateParams() {
		return createParams;
	}
	public void setCreateParams(String createParams) {
		this.createParams = createParams;
	}
	/**
	 * Can you use NULL for this type.<br/> 
	 * typeNoNulls - does not allow NULL values<br/> 
	 * typeNullable - allows NULL values<br/> 
	 * typeNullableUnknown - nullability unknown<br/> 
	 * */ 
	public short getNullable() {
		return nullable;
	}
	public void setNullable(short nullable) {
		this.nullable = nullable;
	}
	/**
	 * Is it case sensitive. 
	 */
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	/**
	 * Can you use "WHERE" based on this type:<br/>
	 * typePredNone - No support <br>
	 * typePredChar - Only supported with WHERE .. LIKE<br/> 
	 * typePredBasic - Supported except for WHERE .. LIKE<br/>
	 * typeSearchable - Supported for all WHERE .. <br/>
	 * */
	public short getSearchable() {
		return searchable;
	}
	public void setSearchable(short searchable) {
		this.searchable = searchable;
	}
	/**
	 * Is it unsigned
	 */
	public boolean isUnsignedAttribute() {
		return unsignedAttribute;
	}
	public void setUnsignedAttribute(boolean unsignedAttribute) {
		this.unsignedAttribute = unsignedAttribute;
	}
	/**
	 * Can it be a money value. 
	 */
	public boolean isFixedPrecScale() {
		return fixedPrecScale;
	}
	public void setFixedPrecScale(boolean fixedPrecScale) {
		this.fixedPrecScale = fixedPrecScale;
	}
	/**
	 * Can it be used for an auto-increment value.
	 */
	public boolean isAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	/**
	 * Localized version of type name (may be null) 
	 */
	public String getLocalTypeName() {
		return localTypeName;
	}
	public void setLocalTypeName(String localTypeName) {
		this.localTypeName = localTypeName;
	}
	/**
	 * Minimum scale supported 
	 */
	public short getMinimumScale() {
		return minimumScale;
	}
	public void setMinimumScale(short minimumScale) {
		this.minimumScale = minimumScale;
	}
	/**
	 * Maximum scale supported
	 */
	public short getMaximumScale() {
		return maximumScale;
	}
	public void setMaximumScale(short maximumScale) {
		this.maximumScale = maximumScale;
	}
	/**
	 * UNUSED
	 */
	public int getSqlDataType() {
		return sqlDataType;
	}
	public void setSqlDataType(int sqlDataType) {
		this.sqlDataType = sqlDataType;
	}
	/**
	 * UNUSED
	 */
	public int getSqlDatetimeSub() {
		return sqlDatetimeSub;
	}
	public void setSqlDatetimeSub(int sqlDatetimeSub) {
		this.sqlDatetimeSub = sqlDatetimeSub;
	}
	/**
	 * Usually 2 or 10
	 */
	public int getNumPrecRadix() {
		return numPrecRadix;
	}
	public void setNumPrecRadix(int numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}

}
