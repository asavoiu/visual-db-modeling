package ro.visualDB.sql.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.model.Catalog;
import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.Constraint;
import ro.visualDB.sql.model.Schema;
import ro.visualDB.sql.model.Table;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;


public class DBInfoProcessor {
	Remote remote;
	IDatabaseConnection databaseConnection;
	
	public DBInfoProcessor(Remote remote) throws Exception {
		this.remote = remote;
		databaseConnection = remote.getConnection();
	}

	
	
	public TreeNode buildTreeForRemote() throws SQLException{
		buildTree();
		return remote;
	}
	
	/**
	 * Mega function that builds the remote tree
	 * populates the catalogs,schemas,tables and columns
	 * @throws SQLException
	 */
	public void buildTree() throws SQLException {
		Statement st = null;
		ResultSet rs = null;
		// badass query
		// don't touch without asking, it might break
		String sql = " SELECT information_schema.schemata.catalog_name as catalog_name, " +
				" information_schema.schemata.schema_name as schema_name, ";
		if (remote.getDatabaseEngine() == SQLEngine.POSTGRES)
			sql += " information_schema.schemata.schema_owner as schema_owner, ";
		sql +=	" information_schema.tables.table_name as table_name, " +
				" information_schema.tables.table_type as table_type, " +
				" information_schema.columns.column_name as column_name, " +
				" information_schema.columns.ordinal_position as ordinal_position, " +
				" information_schema.columns.column_default as column_default, " +
				" information_schema.columns.is_nullable as is_nullable, " +
				" information_schema.columns.data_type as data_type, " +
				" information_schema.columns.character_maximum_length as character_maximum_length, " +
				" information_schema.columns.character_octet_length as character_octet_length, " +
				" information_schema.columns.numeric_precision as numeric_precision, " +
				" information_schema.columns.numeric_scale as numeric_scale, " +
				" information_schema.key_column_usage.constraint_catalog as constraint_catalog, " +
				" information_schema.key_column_usage.constraint_schema as constraint_schema, " +
				" information_schema.key_column_usage.constraint_name as constraint_name, " +
				" information_schema.key_column_usage.table_catalog as constraint_table_catalog, " +
				" information_schema.key_column_usage.table_schema as constraint_table_schema, " +
				" information_schema.key_column_usage.table_name as constraint_table_name, " +
				" information_schema.key_column_usage.column_name as constraint_column_name, " +
				" information_schema.table_constraints.constraint_type as constraint_type " +
		" from information_schema.schemata " +
				" left join information_schema.tables " +
					" on information_schema.schemata.catalog_name = information_schema.tables.table_catalog and " +
					   " information_schema.schemata.schema_name = information_schema.tables.table_schema " +
				" left join information_schema.columns " +
					" on information_schema.tables.table_catalog = information_schema.columns.table_catalog and " + 
					   " information_schema.tables.table_schema = information_schema.columns.table_schema and " +
					   " information_schema.tables.table_name = information_schema.columns.table_name " +
				" left join information_schema.key_column_usage " +
					" on information_schema.columns.table_catalog = information_schema.key_column_usage.table_catalog and " +
					   " information_schema.columns.table_schema = information_schema.key_column_usage.table_schema and " +
					   " information_schema.columns.table_name = information_schema.key_column_usage.table_name and " +
					   " information_schema.columns.column_name = information_schema.key_column_usage.column_name " +
				" left join information_schema.table_constraints " +
					" on information_schema.key_column_usage.constraint_name = information_schema.table_constraints.constraint_name and" +
		((remote.getDatabaseEngine() == SQLEngine.POSTGRES) ?
					   " information_schema.key_column_usage.table_catalog = information_schema.table_constraints.table_catalog and " : "") +
					   " information_schema.key_column_usage.table_schema = information_schema.table_constraints.table_schema and " +
					   " information_schema.key_column_usage.table_name = information_schema.table_constraints.table_name " +
		" order by information_schema.schemata.catalog_name, " +
				" information_schema.schemata.schema_name, " +
				" information_schema.tables.table_name ";
		
		try {
			st = databaseConnection.getConnection().createStatement();
			rs = st.executeQuery(sql);
			Catalog cat = null;
			Schema sch = null;
			Table tbl = null;
			Column c = null;
			
			String catalogName;
			String lastCatalogName = "";
			String lastSchemaName = "";
			String lastTableName = "";
			boolean catalogChanged = false;
			boolean schemaChanged = false;
			
			while (rs.next()) {
				// if new catalog add it to the tree
				if (!lastCatalogName.equals(rs.getString("catalog_name"))) {
					cat = new Catalog();
					cat.setCatalogName(rs.getString("catalog_name"));
					lastCatalogName = cat.getCatalogName();
					catalogChanged = true;
					remote.addChild(cat);
				} else {
					catalogChanged = false;
				}
				// if new schema add it to the tree
				if (!lastSchemaName.equals(rs.getString("schema_name")) ||
						catalogChanged) {
					sch = new Schema();
					sch.setCatalogName(cat.getCatalogName());
					sch.setSchemaName(rs.getString("schema_name"));
					lastSchemaName = sch.getSchemaName();
					schemaChanged = true;
					cat.addChild(sch);
				} else {
					schemaChanged = false;
				}
				
				if (rs.getString("table_name") == null) {
					continue; // empty databases have no tables
				}
				// if new table add it to the tree
				if (!lastTableName.equals(rs.getString("table_name")) ||
						catalogChanged || 
						schemaChanged) {
					tbl = new Table();
					tbl.setTableCatalogName(cat.getCatalogName());
					tbl.setTableSchemaName(sch.getSchemaName());
					tbl.setTableName(rs.getString("table_name"));
					tbl.setTableType(rs.getString("table_type"));
					lastTableName = tbl.getTableName();
					sch.addChild(tbl);
				}

				// add new column to the tree
				c = new Column();
				c.setTableCatalogName(cat.getCatalogName());
				c.setTableSchemaName(sch.getSchemaName());
				c.setTableName(tbl.getTableName());
				c.setColumnName(rs.getString("column_name"));
				c.setOrdinalPosition(rs.getInt("ordinal_position"));
				c.setColumnDefault(rs.getString("column_default"));
				c.setIsNullable(rs.getString("is_nullable"));
				c.setDataType(rs.getString("data_type"));
				c.setCharacterMaximumLength(rs.getLong("character_maximum_length"));
				c.setCharacterOctetLength(rs.getLong("character_octet_length"));
				c.setNumericPrecision(rs.getLong("numeric_precision"));
				c.setNumericScale(rs.getLong("numeric_scale"));				
				
				// add foreign key or public key constraint
				if (rs.getString("constraint_type") != null) {
					Constraint cons =  new Constraint();
					cons.setConstraintCatalog(rs.getString("constraint_catalog"));
					cons.setConstraintSchema(rs.getString("constraint_schema"));
					cons.setConstraintName(rs.getString("constraint_name"));
					cons.setTableCatalog(rs.getString("constraint_table_catalog"));
					cons.setTableSchema(rs.getString("constraint_table_schema"));
					cons.setTableName(rs.getString("constraint_table_name"));
					cons.setColumnName(rs.getString("constraint_column_name"));
					cons.setConstraintType(rs.getString("constraint_type"));
					c.setConstraint(cons);
				}
				
				tbl.addChild(c);
			}
		} finally {
			// cleanup
			if (st != null) {
				try { 
					st.close();
					if (rs != null) {
						rs.close();
					}
				} catch (SQLException sqlEx) {
					// ignore 'Who cares?'
				}
			}
		}
	}
}
