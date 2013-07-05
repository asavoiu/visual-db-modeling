package ro.visualDB.sql.model;

public class Constraint {
	/* Name of the database that contains the constraint
	 * (always the current database) */
    private String constraintCatalog = "";
    // Name of the schema that contains the constraint
    private String constraintSchema = "";
    // Name of the constraint
    private String constraintName = "";
    /* Name of the database that contains the table that contains
     * the column that is restricted by some constraint 
     * (always the current database) */
    private String tableCatalog = "";
    /* Name of the schema that contains the table that contains
     * the column that is restricted by some constraint */
    private String tableSchema = "";
    /* Name of the table that contains the column
     * that is restricted by some constraint
     */
    private String tableName = "";
    // Name of the column that is restricted by some constraint
    private String columnName = "";
    /* Type of the constraint: CHECK (only available for Postgres),
     * FOREIGN KEY, PRIMARY KEY, or UNIQUE */
    private String constraintType = "";
    
	public String getConstraintCatalog() {
		return constraintCatalog;
	}
	public void setConstraintCatalog(String constraintCatalog) {
		this.constraintCatalog = constraintCatalog;
	}
	public String getConstraintSchema() {
		return constraintSchema;
	}
	public void setConstraintSchema(String constraintSchema) {
		this.constraintSchema = constraintSchema;
	}
	public String getConstraintName() {
		return constraintName;
	}
	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}
	public String getTableCatalog() {
		return tableCatalog;
	}
	public void setTableCatalog(String tableCatalog) {
		this.tableCatalog = tableCatalog;
	}
	public String getTableSchema() {
		return tableSchema;
	}
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getConstraintType() {
		return constraintType;
	}
	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Constraint) {
			Constraint tn = (Constraint)obj;
			if (constraintCatalog.equals(tn.getConstraintCatalog()) &&
	            constraintSchema.equals(tn.getConstraintSchema()) &&
	            constraintName.equals(tn.getConstraintName()) &&
	            tableCatalog.equals(tn.getTableCatalog()) &&
	            tableSchema.equals(tn.getTableSchema()) &&
	            tableName.equals(tn.getTableName()) &&
	            columnName.equals(tn.getColumnName()) &&
	            constraintType.equals(tn.getConstraintType())) {
				return true;
			}
			return false;
		} else {
			return false;
		}
	}
}
