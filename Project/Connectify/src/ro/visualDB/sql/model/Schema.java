package ro.visualDB.sql.model;

public class Schema {
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
}
