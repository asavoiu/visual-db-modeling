package ro.visualDB.sql.model;

public class ForeignKeys {
    private String primaryKeyCatalogName;
    private String primaryKeySchemaName;
    private String primaryKeyTableName;
    private String primaryKeyColumnName;
    private String foreignKeyCatalogName;
    private String foreignKeySchemaName;
    private String foreignKeyTableName;
    private String foreignKeyColumnName;

    public String getPrimaryKeyCatalogName() {
        return primaryKeyCatalogName;
    }

    public void setPrimaryKeyCatalogName(String primaryKeyCatalogName) {
        this.primaryKeyCatalogName = primaryKeyCatalogName;
    }

    public String getPrimaryKeySchemaName() {
        return primaryKeySchemaName;
    }

    public void setPrimaryKeySchemaName(String primaryKeySchemaName) {
        this.primaryKeySchemaName = primaryKeySchemaName;
    }

    public String getPrimaryKeyTableName() {
        return primaryKeyTableName;
    }

    public void setPrimaryKeyTableName(String primaryKeyTableName) {
        this.primaryKeyTableName = primaryKeyTableName;
    }

    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnName;
    }

    public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
        this.primaryKeyColumnName = primaryKeyColumnName;
    }

    public String getForeignKeyCatalogName() {
        return foreignKeyCatalogName;
    }

    public void setForeignKeyCatalogName(String foreignKeyCatalogName) {
        this.foreignKeyCatalogName = foreignKeyCatalogName;
    }

    public String getForeignKeySchemaName() {
        return foreignKeySchemaName;
    }

    public void setForeignKeySchemaName(String foreignKeySchemaName) {
        this.foreignKeySchemaName = foreignKeySchemaName;
    }

    public String getForeignKeyTableName() {
        return foreignKeyTableName;
    }

    public void setForeignKeyTableName(String foreignKeyTableName) {
        this.foreignKeyTableName = foreignKeyTableName;
    }

    public String getForeignKeyColumnName() {
        return foreignKeyColumnName;
    }

    public void setForeignKeyColumnName(String foreignKeyColumnName) {
        this.foreignKeyColumnName = foreignKeyColumnName;
    }
}
