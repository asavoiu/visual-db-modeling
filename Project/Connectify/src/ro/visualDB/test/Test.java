package ro.visualDB.test;

import ro.visualDB.initiate.connections.MySQLDatabaseConnection;
import ro.visualDB.logging.AppLogger;
import ro.visualDB.xml.Tree;

public class Test {
	
	public static void main(String[] args) {
		AppLogger.getLogger().info("Start Test");
		try{ 
//			MySQLDatabaseConnection dbConnectionMySQL = new MySQLDatabaseConnection(
//					"jdbc:mysql://127.0.0.1/",
//					"root",
//					"",
//                    "123",
//					"pw"
//            );
//			Tree tree = dbConnectionMySQL.getSchema();
//			tree.print();
//			XMLWriter.writeToFile("C:\\Users\\Auras\\git\\visual-db-modeling\\test.xml", tree);
		} catch (Exception e) {
			e.printStackTrace();
			AppLogger.getLogger().error("Error", e);
		}
	}
}
