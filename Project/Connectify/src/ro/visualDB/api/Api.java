package ro.visualDB.api;

import java.util.ArrayList;

import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.helpers.DBInfoProcessor;
import ro.visualDB.sql.helpers.SQLQueryExecutorHelper;
import ro.visualDB.sql.model.Catalog;
import ro.visualDB.sql.model.Schema;
import ro.visualDB.sql.model.Table;
import ro.visualDB.versioning.Versioning;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLReader;
import ro.visualDB.xml.XMLWriter;

public class Api {

	
	public static void exportToXML(TreeNode rootTn, String xmlFile) throws Exception {
		XMLWriter.writeToFile(xmlFile, rootTn);			
	}
	
	public static TreeNode importFromXML(String xmlFile) throws Exception {
		return XMLReader.readFromFile(xmlFile);
	}
	
	public static TreeNode importFromRemote(Remote rmt) throws Exception {
		DBInfoProcessor dbip = new DBInfoProcessor(rmt);						
		return dbip.buildTreeForRemote();
	}
	
	public static String getCreateSQLScripts(TreeNode treeNode, int engine) throws Exception {
		return treeNode.getCreateSqlStatement(engine);
	}
	
	public static String getCreateSQLScriptsOfTreeNodeAndChildren(TreeNode treeNode, int engine) throws Exception {
		String sql = treeNode.getCreateSqlStatement(engine);
		for (TreeNode tn : treeNode.children) {
			String s = getCreateSQLScriptsOfTreeNodeAndChildren(tn, engine);
			if (sql != null) 
				sql += s != null ? "\n" + s : "";
			else 
				sql = s != null ? "\n" + s : null;
		}
		return sql;
	}
	
	public static String getModifySqlStatement(TreeNode treeNode, int engine) throws Exception {
		return treeNode.getModifySqlStatement(engine);
	}
	
	public static TreeNode getTreeWithFlags(String xmlSourceVersionFile,
			String xmlDestVersionFile) throws Exception {
		return Versioning.getTreeWithFlags(xmlSourceVersionFile, xmlDestVersionFile);
	}
	
	public static TreeNode getTreeWithFlags(TreeNode sourceNode,
			TreeNode destNode) throws Exception {
		return Versioning.getTreeWithFlags(sourceNode, destNode);
	}
	
	public static boolean executeQueryOnDatabase(Remote rmt, String database, String query) throws Exception {
		return SQLQueryExecutorHelper.executeQuery(rmt.getConnection().getConnection(database), query);
	}
	
	public static Catalog findCatalog(Remote rmt, String name) throws Exception {
		for (TreeNode tn : rmt.getChildren()) {
			Catalog c = (Catalog) tn;
			if (c.getCatalogName().equals(name))
				return c;
		}
		return null;
	}
	
	public static Schema findSchema(Remote rmt, String name) throws Exception {
		for (TreeNode tn : rmt.getChildren()) {
			for (TreeNode tn2 : tn.getChildren()) {
				Schema s = (Schema) tn2;
				if (s.getSchemaName().equals(name))
					return s;
			}
		}
		return null;
	}
	
	public static Schema findSchema(Remote rmt, String catalogName, String name) throws Exception {
		for (TreeNode tn : rmt.getChildren()) {
			Catalog c = (Catalog) tn;
			if (c.getCatalogName().equals(catalogName)) {
				for (TreeNode tn2 : tn.getChildren()) {
					Schema s = (Schema) tn2;
					if (s.getSchemaName().equals(name))
						return s;
				}
			}
		}
		return null;
	}
	
	public static Table findTable(Remote rmt, String name) throws Exception {
		for (TreeNode tn : rmt.getChildren()) {
			for (TreeNode tn2 : tn.getChildren()) {
				for (TreeNode tn3 : tn.getChildren()) {
					Table t = (Table) tn3;
					if (t.getTableName().equals(name))
						return t;
				}
			}
		}
		return null;
	}
	
	public static Table findTable(Remote rmt, String catalogName, String schemaName, String name) throws Exception {
		for (TreeNode tn : rmt.getChildren()) {
			Catalog c = (Catalog) tn;
			if (c.getCatalogName().equals(catalogName)) {
				for (TreeNode tn2 : tn.getChildren()) {
					Schema s = (Schema) tn2;
					if (s.getSchemaName().equals(schemaName))
						for (TreeNode tn3 : tn2.getChildren()) {
							Table t = (Table) tn3;
							if (t.getTableName().equals(name))
								return t;
						}
				}
			}
		}
		return null;
	}

}
