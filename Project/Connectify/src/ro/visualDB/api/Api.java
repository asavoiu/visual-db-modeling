package ro.visualDB.api;

import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.helpers.DBInfoProcessor;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLReader;
import ro.visualDB.xml.XMLWriter;

public class Api {

	
	public void exportToXML(TreeNode rootTn, String xmlFile) throws Exception {
		XMLWriter.writeToFile(xmlFile, rootTn);			
	}
	
	public TreeNode importFromXML(String xmlFile) throws Exception {
		return XMLReader.readFromFile(xmlFile);
	}
	
	public TreeNode importFromRemote(Remote rmt) throws Exception {
		DBInfoProcessor dbip = new DBInfoProcessor(rmt.getConnection());						
		return dbip.buildTreeForRemoteConnectionTreeNode(rmt);
	}
	
	public String getCreateSQLScripts(TreeNode treeNode, int engine) throws Exception {
		return treeNode.getSqlStatement(engine);
	}
	
	public String getModifySqlStatement(TreeNode treeNode, int engine) throws Exception {
		return treeNode.getModifySqlStatement(engine);
	}
}