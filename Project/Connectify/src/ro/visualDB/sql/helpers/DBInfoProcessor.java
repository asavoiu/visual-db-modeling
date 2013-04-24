package ro.visualDB.sql.helpers;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.model.*;
import ro.visualDB.xml.TreeNode;


public class DBInfoProcessor {
	IDatabaseConnection databaseConnection;
	DatabaseMetaData dbm;

	public DBInfoProcessor(IDatabaseConnection dbConnection) throws SQLException {
		databaseConnection = dbConnection;
		this.dbm = databaseConnection.getConnection().getMetaData();
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
				try{
					t.setRemarks(rs.getString(5));
				} catch (SQLException e) {}
				try{
					t.setTypesCatalog(rs.getString(6));
				} catch (SQLException e) {}
				try{
						t.setTypesSchema(rs.getString(7));
				} catch (SQLException e) {}
				try{
					t.setTypeName(rs.getString(8));
				} catch (SQLException e) {}
				try{
					t.setSelfRefrencingColName(rs.getString(9));
				} catch (SQLException e) {}
				try{
					t.setRefGeneration(rs.getString(10));
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
                    c.setPrimaryKey(myPkColumn.equals(rs.getString("COLUMN_NAME")));
                    c.setForeignKey(findTheFKs(foreignKeys,rs.getString("COLUMN_NAME"))!=-1);
                    if(c.isForeignKey()){
                        c.setForeignKeys(foreignKeys.get(findTheFKs(foreignKeys,rs.getString("COLUMN_NAME"))));
                    }
                } catch (SQLException e) {}
				try {
					c.setDataType(rs.getString("DATA_TYPE"));
				} catch (SQLException e) {}
				try {
					c.setTypeName(rs.getString("TYPE_NAME"));
				} catch (SQLException e) {}
				try {
					c.setColumnSize(rs.getInt("COLUMN_SIZE"));
				} catch (SQLException e) {}
				//skipp bufferLength;
				try {
					c.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
				} catch (SQLException e) {}
				try {
					c.setNumPrecRadix(rs.getInt("NUM_PREC_RADIX"));
				} catch (SQLException e) {}
				try {
					c.setNullable(rs.getInt("NULLABLE"));
				} catch (SQLException e) {}
				try {
					c.setRemarks(rs.getString("REMARKS"));
				} catch (SQLException e) {}
				try {
					c.setColumnDef(rs.getString("COLUMN_DEF"));
				} catch (SQLException e) {}
				try {
					c.setSqlDataType(rs.getInt("SQL_DATA_TYPE"));
				} catch (SQLException e) {}
				try {
					c.setSqlDateTimeSub(rs.getInt("SQL_DATETIME_SUB"));
				} catch (SQLException e) {}
				try {
					c.setCharOctetLength(rs.getInt("CHAR_OCTET_LENGTH"));
				} catch (SQLException e) {}
				try {
					c.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
				} catch (SQLException e) {}
				try {
					c.setIsNullable(rs.getString("IS_NULLABLE"));
				} catch (SQLException e) {}
				try {
					c.setScopeCatalog(rs.getString("SCOPE_CATALOG"));
				} catch (SQLException e) {}
				try {
					c.setScopeSchema(rs.getString("SCOPE_SCHEMA"));
				} catch (SQLException e) {}
				try {
					c.setScopeTable(rs.getString("SCOPE_TABLE"));
				} catch (SQLException e) {}
				try {
					c.setSourceDataType(rs.getShort("SOURCE_DATA_TYPE"));
				} catch (SQLException e) {}
				try {
					c.setIsAutoIncrement(rs.getString("IS_AUTOINCREMENT"));
				} catch (SQLException e) {}
				try {
					c.setIsGeneratedColumn(rs.getString("IS_GENERATEDCOLUMN"));
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
		TreeNode treeNodeTb = new TreeNode(tb);
		ArrayList<Column> clms = getColumns(tb.getTableCatalogName(), tb.getTableName());
		for (Column clm : clms) {
			TreeNode treeNodeClm = new TreeNode(clm);
			treeNodeTb.addChild(treeNodeClm);
		}
		return treeNodeTb;
	}

	public TreeNode buildTreeForSchemaNode(Schema sc) throws SQLException {
		TreeNode treeNodeSc = new TreeNode(sc);
		ArrayList<Table> ts = getTables(sc.getCatalogName(), sc.getSchemaName());
		for (Table tb : ts) {
			TreeNode treeNodeTb = buildTreeForTableNode(tb);
			treeNodeSc.addChild(treeNodeTb);
		}
		return treeNodeSc;
	}

	public TreeNode buildTreeForCatalogNode(Catalog ct) throws SQLException {
		TreeNode treeNodeCt = new TreeNode(ct);
		ArrayList<Schema> schemas = getSchemas(ct.getCatalogName());
		if (schemas.size() != 0) {
			for (Schema sc : schemas) {
                if(!(sc.getSchemaName().equals("information_schema")) && !(sc.getSchemaName().equals("pg_catalog")) && !(sc.getSchemaName().equals("public"))){
                    sc.setCatalogName(ct.getCatalogName());
                    TreeNode treeNodeSc = buildTreeForSchemaNode(sc);
                    treeNodeCt.addChild(treeNodeSc);
                }
			}
		} else {
            ArrayList<Table> ts = getTables(ct.getCatalogName());
			for (Table tb : ts) {
				TreeNode treeNodeTb = buildTreeForTableNode(tb);
				treeNodeCt.addChild(treeNodeTb);
			}
		}
		return treeNodeCt;
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
}
