package ro.visualDB.sql.helpers;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.model.Catalog;
import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.Constraint;
import ro.visualDB.sql.model.DataType;
import ro.visualDB.sql.model.ForeignKeys;
import ro.visualDB.sql.model.Schema;
import ro.visualDB.sql.model.Table;
import ro.visualDB.sql.model.TableType;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;


public class DBInfoProcessor {
	Remote remote;
	IDatabaseConnection databaseConnection;
	DatabaseMetaData dbm;

	public DBInfoProcessor(IDatabaseConnection dbConnection) throws SQLException {
		databaseConnection = dbConnection;
		this.dbm = databaseConnection.getConnection().getMetaData();
	}
	
	public DBInfoProcessor(Remote remote) throws Exception {
		this.remote = remote;
		databaseConnection = remote.getConnection();
	}

	/**
	 * Gets the supported Data Types of the database.
	 * @return ArrayList types
	 */
	public ArrayList<DataType> getSupportedDataTypes() throws SQLException {
		ResultSet rs = null;

		ArrayList<DataType> types = new ArrayList<DataType>();
		try {
			rs = dbm.getTypeInfo();
			while (rs.next()) {
				DataType type = new DataType();
				/* We need to do a try catch on every column because
				 * some of the columns may not be present in the resultSet
				 */

				try {
					type.setTypeName(rs.getString("TYPE_NAME"));
				} catch (SQLException e) {}
				try {
					type.setDataType(rs.getInt("DATA_TYPE"));
				} catch (SQLException e) {}
				try {
					type.setPrecision(rs.getInt("PRECISION"));
				} catch (SQLException e) {}
				try {
					type.setLiteralPrefix(rs.getString("LITERAL_PREFIX"));
				} catch (SQLException e) {}
				try {
					type.setLiteralSuffix(rs.getString("LITERAL_SUFFIX"));
				} catch (SQLException e) {}
				try {
					type.setCreateParams(rs.getString("CREATE_PARAMS"));
				} catch (SQLException e) {}
				try {
					type.setNullable(rs.getShort("NULLABLE"));
				} catch (SQLException e) {}
				try {
					type.setCaseSensitive(rs.getBoolean("CASE_SENSITIVE"));
				} catch (SQLException e) {}
				try {
					type.setSearchable(rs.getShort("SEARCHABLE"));
				} catch (SQLException e) {}
				try {
					type.setUnsignedAttribute(rs.getBoolean("UNSIGNED_ATTRIBUTE"));
				} catch (SQLException e) {}
				try {
					type.setFixedPrecScale(rs.getBoolean("FIXED_PREC_SCALE"));
				} catch (SQLException e) {}
				try {
					type.setAutoIncrement(rs.getBoolean("AUTO_INCREMENT"));
				} catch (SQLException e) {}
				try {
					type.setLocalTypeName(rs.getString("LOCAL_TYPE_NAME"));
				} catch (SQLException e) {}
				try {
					type.setMinimumScale(rs.getShort("MINIMUM_SCALE"));
				} catch (SQLException e) {}
				try {
					type.setMaximumScale(rs.getShort("MAXIMUM_SCALE"));
				} catch (SQLException e) {}
				try {
					type.setSqlDataType(rs.getInt("SQL_DATA_TYPE"));
				} catch (SQLException e) {}
				try {
					type.setSqlDatetimeSub(rs.getInt("SQL_DATETIME_SUB"));
				} catch (SQLException e) {}
				try {
					type.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX"));
				} catch (SQLException e) {}
				types.add(type);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
		    }
		}
		return types;
	}

	/**
	 * Gets the supported Table Types of the database.
	 * @return ArrayList types
	 */
	public ArrayList<TableType> getSupportedTableTypes() throws SQLException {
		ResultSet rs = null;
		ArrayList<TableType> types = new ArrayList<TableType>();
		try{
			rs = dbm.getTableTypes();
			while (rs.next()) {
				TableType type = new TableType();
				type.setTableType(rs.getString(1));
				types.add(type);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
		    }
		}
		return types;
	}

	/**
	 * Gets the Available Catalogs of the database.
	 */
	public ArrayList<Catalog> getCatalogs() throws SQLException {
		ResultSet rs = null;
		ArrayList<Catalog> catalogs = new ArrayList<Catalog>();
		String catTerm = dbm.getCatalogTerm();
		try {
			rs = dbm.getCatalogs();
			while (rs.next()) {
				Catalog catalog = new Catalog();
				catalog.setCatalogName(rs.getString(1));
				catalog.setCatalogTerm(catTerm);
				catalogs.add(catalog);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
		    }
		}
		return catalogs;
	}

	/**
	 * Gets the Available Schemas of the database.
	 */
	public ArrayList<Schema> getSchemas() throws SQLException {
		String schemaTerm = dbm.getSchemaTerm();
		ResultSet rs = null;
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		try{
			rs = dbm.getSchemas();
			while (rs.next()) {
				Schema schema = new Schema();
				schema.setSchemaName(rs.getString(1));
				try {
					schema.setCatalogName(rs.getString(2));
				} catch (SQLException e) {}
				schema.setSchemaTerm(schemaTerm);
				schemas.add(schema);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
		    }
		}
		return schemas;
	}

	/**
	 * Gets the Schemas of a Catalog.
	 */
	public ArrayList<Schema> getSchemas(String catalog) throws SQLException {
		String schemaTerm = dbm.getSchemaTerm();
		ResultSet rs = null;
		ArrayList<Schema> schemas = new ArrayList<Schema>();
		try{
			rs = dbm.getSchemas(catalog, null);
			while (rs.next()) {
				Schema schema = new Schema();
				schema.setSchemaName(rs.getString(1));
				try {
					schema.setCatalogName(rs.getString(2));
				} catch (SQLException e) {}
				schema.setSchemaTerm(schemaTerm);
				schemas.add(schema);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
		    }
		}
		return schemas;
	}

	/**
	 *  Returns all the tables in the DB that satisfy
	 *  the given patterns
	 **/
	public ArrayList<Table> getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {

		ResultSet rs = null;
		ArrayList<Table> tables = new ArrayList<Table>();
		try{

			rs = dbm.getTables(catalog, schemaPattern, tableNamePattern, types);
			while (rs.next()) {
				Table t = new Table();
				/* We need to do a try catch on every column because
				 * some of the columns may not be present in the resultSet
				 */
				try{
						t.setTableCatalogName(rs.getString(1));
				} catch (SQLException e) {}
				try{
					t.setTableSchemaName(rs.getString(2));
				} catch (SQLException e) {}
				try{
					t.setTableName(rs.getString(3));
				} catch (SQLException e) {}
				try{
					t.setTableType(rs.getString(4));
				} catch (SQLException e) {}
				
				tables.add(t);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
		    }
		}
		return tables;
	}

	public ArrayList<Table> getTables(String catalog) throws SQLException {
		return getTables(catalog, null, "%", null);
	}

	public ArrayList<Table> getTables(String catalog,
			String schemaPattern) throws SQLException {
		return getTables(catalog, schemaPattern, "%", null);
	}

	public ArrayList<Table> getTables(String catalog,
			String schemaPattern,
			String[] types) throws SQLException {
		return getTables(catalog, schemaPattern, "%", types);
	}

	public ArrayList<Table> getTables() throws SQLException {
		return getTables(null, null, "%", null);
	}

    public int findTheFKs(ArrayList<ForeignKeys> foreignKeys, String columnName){
        int position=0;
        for(ForeignKeys fks: foreignKeys){
            if(columnName.equals(fks.getForeignKeyColumnName())){
                return position;
            }
            position++;
        }
        return -1;
    }

	public ArrayList<Column> getColumns(String catalog, String schemaPattern, String tableNamePattern,
                                        String columnNamePattern) throws SQLException {
		ResultSet rs = null;
        ResultSet rsPks = null;
        ResultSet rsFks = null;
        String myPkColumn = "";
		ArrayList<Column> columns = new ArrayList<Column>();
        ArrayList<ForeignKeys> foreignKeys = new ArrayList<ForeignKeys>();

		try{
			rs = dbm.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
            rsPks = dbm.getPrimaryKeys(catalog, schemaPattern, tableNamePattern);
            rsFks = dbm.getImportedKeys(catalog, schemaPattern, tableNamePattern);

            //We take all the FKs in the tables
            while(rsFks.next()){
                ForeignKeys tmpFKElem = new ForeignKeys();

                try {
                    tmpFKElem.setPrimaryKeySchemaName(rsFks.getString("PKTABLE_SCHEM"));
                } catch (SQLException e) {}
                try {
                    tmpFKElem.setPrimaryKeyTableName(rsFks.getString("PKTABLE_NAME"));
                } catch (SQLException e) {}
                try {
                    tmpFKElem.setPrimaryKeyColumnName(rsFks.getString("PKCOLUMN_NAME"));
                } catch (SQLException e) {}
                try {
                    tmpFKElem.setForeignKeyColumnName(rsFks.getString("FKCOLUMN_NAME"));
                } catch (SQLException e) {}
                foreignKeys.add(tmpFKElem);
            }

            //We take all the PKs in the tables
            while(rsPks.next()){
                try {
                    myPkColumn = rsPks.getString("COLUMN_NAME");
                } catch (SQLException e) {}
            }
			while (rs.next()) {
				Column c = new Column();
				/* We need to do a try catch on every column because
				 * some of the columns may not be present in the resultSet
				 */

				try {
					c.setTableCatalogName(rs.getString("TABLE_CAT"));
				} catch (SQLException e) {}
				try {
					c.setTableSchemaName(rs.getString("TABLE_SCHEM"));
				} catch (SQLException e) {}
				try {
					c.setTableName(rs.getString("TABLE_NAME"));
				} catch (SQLException e) {}
				try {
					c.setColumnName(rs.getString("COLUMN_NAME"));
                } catch (SQLException e) {}
				try {
					c.setDataType(rs.getString("DATA_TYPE"));
				} catch (SQLException e) {}
				
				try {
					c.setColumnDefault(rs.getString("COLUMN_DEF"));
				} catch (SQLException e) {}
				
				try {
					c.setCharacterOctetLength(rs.getInt("CHAR_OCTET_LENGTH"));
				} catch (SQLException e) {}
				try {
					c.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
				} catch (SQLException e) {}
				try {
					c.setIsNullable(rs.getString("IS_NULLABLE"));
				} catch (SQLException e) {}
				
				
				columns.add(c);
			}
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
					// ignore
				}
		    }
		}
		return columns;
	}

	public ArrayList<Column> getColumns(String catalog,
			String schemaPattern,
			String tableNamePattern) throws SQLException {
		return getColumns(catalog, schemaPattern, tableNamePattern, "%");
	}

	public ArrayList<Column> getColumns(String catalog,
			String tableNamePattern) throws SQLException {
		return getColumns(catalog, null, tableNamePattern, "%");
	}

	public TreeNode buildTreeForTableNode(Table tb) throws SQLException {
		ArrayList<Column> clms = getColumns(tb.getTableCatalogName(), tb.getTableName());
		for (Column clm : clms) {
			tb.addChild(clm);
		}
		return tb;
	}

	public TreeNode buildTreeForSchemaNode(Schema sc) throws SQLException {
		ArrayList<Table> ts = getTables(sc.getCatalogName(), sc.getSchemaName());
		for (Table tb : ts) {
			TreeNode treeNodeTb = buildTreeForTableNode(tb);
			sc.addChild(treeNodeTb);
		}
		return sc;
	}

	public TreeNode buildTreeForCatalogNode(Catalog ct) throws SQLException {
		ArrayList<Schema> schemas = getSchemas(ct.getCatalogName());
		if (schemas.size() != 0) {
			for (Schema sc : schemas) {
                if(!(sc.getSchemaName().equals("information_schema")) && !(sc.getSchemaName().equals("pg_catalog")) && !(sc.getSchemaName().equals("public"))){
                    sc.setCatalogName(ct.getCatalogName());
                    TreeNode treeNodeSc = buildTreeForSchemaNode(sc);
                    ct.addChild(treeNodeSc);
                }
			}
		} else {
            ArrayList<Table> ts = getTables(ct.getCatalogName());
			for (Table tb : ts) {
				TreeNode treeNodeTb = buildTreeForTableNode(tb);
				ct.addChild(treeNodeTb);
			}
		}
		return ct;
	}

	public TreeNode buildTreeForRemoteConnection() throws SQLException{
		TreeNode tree = new TreeNode(databaseConnection);
		ArrayList<Catalog> catalogs = getCatalogs();
		for (Catalog ct : catalogs) {
			TreeNode treeNodeCt = buildTreeForCatalogNode(ct);
			tree.addChild(treeNodeCt);
		}
		return tree;
	}
	
	public TreeNode buildTreeForRemoteConnectionTreeNode(TreeNode remoteTreeNode) throws SQLException{
		ArrayList<Catalog> catalogs = getCatalogs();
		for (Catalog ct : catalogs) {
			TreeNode treeNodeCt = buildTreeForCatalogNode(ct);
			remoteTreeNode.addChild(treeNodeCt);
		}
		return remoteTreeNode;
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
