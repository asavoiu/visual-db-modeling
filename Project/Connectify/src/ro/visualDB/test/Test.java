package ro.visualDB.test;

import ro.visualDB.logging.AppLogger;
import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.connection.MySQLDatabaseConnection;
import ro.visualDB.sql.connection.PostgreSQLDatabaseConnection;
import ro.visualDB.sql.helpers.DBInfoProcessor;
import ro.visualDB.xml.Tree;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLWriter;

public class Test {

	public static void main(String[] args) {
		AppLogger.getLogger().info("Start Test");
		try{ 
			/*MySQLDatabaseConnection dbConnectionMySQL = new MySQLDatabaseConnection(
					"127.0.0.1",
                    "3306",
					"root",
					""
            );*/
            /*IDatabaseConnection dbConn = new MySQLDatabaseConnection(
					"instance43492.db.xeround.com",
                    "8907",
					"octavyan55",
					"q1w2e3"
            );*/
			
			IDatabaseConnection dbConn2 = new MySQLDatabaseConnection(
					"localhost",
                    "3306",
					"root",
					""
            );
			
            IDatabaseConnection dbConn = new PostgreSQLDatabaseConnection(
					"ec2-23-21-161-153.compute-1.amazonaws.com",
                    "5432",
					"ikqepbqiwxmcwe",
					"cI6PNkfjz4SajHnobEeCHwmvfv",
					"dbtooekfdenm82",
					true
            );
			DBInfoProcessor dbip = new DBInfoProcessor(dbConn);
			
			TreeNode tn = dbip.buildTreeForRemoteConnection();
			tn.print(2);
			
			Tree tree = new Tree(tn);
			XMLWriter.writeToFile("C:\\Users\\Auras\\git\\visual-db-modeling\\Project\\Connectify\\test.xml", tree);
		} catch (Exception e) {
			e.printStackTrace();
			AppLogger.getLogger().error("Error", e);
		}
	}
}
