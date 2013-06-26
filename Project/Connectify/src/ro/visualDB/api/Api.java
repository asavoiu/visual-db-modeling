package ro.visualDB.api;

import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.helpers.DBInfoProcessor;
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
		DBInfoProcessor dbip = new DBInfoProcessor(rmt.getConnection());						
		return dbip.buildTreeForRemoteConnectionTreeNode(rmt);
	}
	
	public static String getCreateSQLScripts(TreeNode treeNode, int engine) throws Exception {
		return treeNode.getSqlStatement(engine);
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
}
