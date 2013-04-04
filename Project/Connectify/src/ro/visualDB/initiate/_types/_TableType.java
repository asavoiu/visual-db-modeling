package ro.visualDB.initiate._types;

public enum _TableType {
	BASE_TABLE("BASE TABLE", 0),
	VIEW("VIEW", 1),
	TEMPORARY("LOCAL TEMPORARY", 1),
	UNKNOWN("UNKNOWN", 2);

	/*-- From MYSQL documentation:
	 * TABLE_TYPE should be BASE TABLE or VIEW.
	 * Currently, the TABLES table does not list TEMPORARY tables.
	 *-- From POSTGRE documentation: 
	 * Type of the table: BASE TABLE for a persistent base table (the normal table type),
	 * VIEW for a view, or LOCAL TEMPORARY for a temporary table
	 */
	private String typeName;
	private int type;
	
	private _TableType(String typeName, int type){
		this.typeName = typeName;
		this.type = type;
	}
	
	public static _TableType getTableType(String name){
		for (_TableType t: _TableType.values())
			if(t.getTypeName().equals(name))
				return t;
		return UNKNOWN;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getType() {
		return type;
	}
}
