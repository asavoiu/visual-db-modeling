package ro.visualDB.sql.model;

/**
 * Class describing SQL Table Types available 
 * in a database.<br/>
 * The table type is a String.
 */
public class TableType {
	private String tableType;

	/** Table type.<br>
	 * Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
	 */
	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	} 

}
