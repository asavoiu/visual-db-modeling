package ro.dbviz.test;

import ro.dbviz.logging.AppLogger;
import ro.dbviz.sql.mysqli.DatabaseConnection;
import ro.dbviz.xml.Tree;
import ro.dbviz.xml.XMLWriter;

public class Test {
	
	public static void main(String[] args) {
		AppLogger.getLogger().info("Start Test");
		try{ 
			DatabaseConnection dbConnection = new DatabaseConnection(
					"jdbc:mysql://127.0.0.1/",
					"root",
					"",
					"pw"
					);
			Tree tree = dbConnection.getSchema();
			tree.print();
			XMLWriter.writeToFile("C:\\Users\\Auras\\git\\visual-db-modeling\\test.xml", tree);
		} catch (Exception e) {
			e.printStackTrace();
			AppLogger.getLogger().error("Error", e);
		}
	}

}
