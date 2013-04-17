package ro.visualDB.sql.helpers;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.model.Catalog;
import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.DataType;
import ro.visualDB.sql.model.Schema;
import ro.visualDB.sql.model.Table;
import ro.visualDB.sql.model.TableType;
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
				type.setTypeName(rs.getString(1));
				type.setDataType(rs.getInt(2));
				type.setPrecision(rs.getInt(3));
				type.setLiteralPrefix(rs.getString(4));
				type.setLiteralSuffix(rs.getString(5));
				type.setCreateParams(rs.getString(6));
				type.setNullable(rs.getShort(7));
				type.setCaseSensitive(rs.getBoolean(8));
				type.setSearchable(rs.getShort(9));
				type.setUnsignedAttribute(rs.getBoolean(10));
				type.setFixedPrecScale(rs.getBoolean(11));
				type.setAutoIncrement(rs.getBoolean(12));
				type.setLocalTypeName(rs.getString(13));
				type.setMinimumScale(rs.getShort(14));
				type.setMaximumScale(rs.getShort(15));
				type.setSqlDataType(rs.getInt(16));
				type.setSqlDatetimeSub(rs.getInt(17));
				type.setNumPrecRadix(rs.getInt(18));
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
		try {
			rs = dbm.getCatalogs();
			while (rs.next()) {
				Catalog catalog = new Catalog();
				catalog.setCatalogName(rs.getString(1));
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
				schema.setCatalogName(rs.getString(2));
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
				schema.setCatalogName(rs.getString(2));
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
	public ArrayList<Table> getTables(String catalog,
			String schemaPattern,
			String tableNamePattern,
			String[] types) throws SQLException {
		ResultSet rs = null;
		ArrayList<Table> tables = new ArrayList<Table>();
		try{
			rs = dbm.getTables(catalog, schemaPattern, tableNamePattern, types);
			while (rs.next()) {
				Table t = new Table();
				try{ 
					t.setTableCatalogName(rs.getString(1));
					t.setTableSchemaName(rs.getString(2));
					t.setTableName(rs.getString(3));
					t.setTableType(rs.getString(4));
					t.setRemarks(rs.getString(5));
					t.setTypesCatalog(rs.getString(6));
					t.setTypesSchema(rs.getString(7));
					t.setTypeName(rs.getString(8));
					t.setSelfRefrencingColName(rs.getString(9));
					t.setRefGeneration(rs.getString(10));
				} catch (SQLException e) {
					// Silently ignore(so silent you can sleep)
					// not all fields may be returned
				}
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
	
	public ArrayList<Column> getColumns(String catalog,
			String schemaPattern,
			String tableNamePattern,
			String columnNamePattern) throws SQLException {
		ResultSet rs = null;
		ArrayList<Column> columns = new ArrayList<Column>();
		try{ 
			rs = dbm.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
			while (rs.next()) {
				Column c = new Column();
				c.setTableCatalogName(rs.getString(1));
				c.setTableSchemaName(rs.getString(2));
				c.setTableName(rs.getString(3));
				c.setColumnName(rs.getString(4));
				c.setDataType(rs.getInt(5));
				c.setTypeName(rs.getString(6));
				c.setColumnSize(rs.getInt(7));
				//skipp bufferLength;
				c.setDecimalDigits(rs.getInt(8));
				c.setNumPrecRadix(rs.getInt(9));
				c.setNullable(rs.getInt(10));
				c.setRemarks(rs.getString(11));
				c.setColumnDef(rs.getString(12));
				//c.setSqlDataType(rs.getInt(13));
				c.setSqlDateTimeSub(rs.getInt(14));
				c.setCharOctetLength(rs.getInt(15));
				c.setOrdinalPosition(rs.getInt(16));
				c.setIsNullable(rs.getString(17));
				c.setScopeCatalog(rs.getString(18));
				c.setScopeSchema(rs.getString(19));
				c.setScopeTable(rs.getString(20));
				c.setSourceDataType(rs.getShort(21));
				c.setIsAutoIncrement(rs.getString(22));
				c.setIsGeneratedColumn(rs.getString(23));
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
				sc.setCatalogName(ct.getCatalogName());
				TreeNode treeNodeSc = buildTreeForSchemaNode(sc);
				treeNodeCt.addChild(treeNodeSc);
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
