package ro.visualDB.test;

import ro.visualDB.logging.AppLogger;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.connection.MySQLDatabaseConnection;
import ro.visualDB.sql.helpers.DBInfoProcessor;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLReader;
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
//            IDatabaseConnection dbConn2 = new MySQLDatabaseConnection(
//					"instance43492.db.xeround.com",
//                    "8907",
//					"octavyan55",
//					"q1w2e3",
//                    ""
//            );
			
			IDatabaseConnection dbConn = new MySQLDatabaseConnection(
					"localhost",
                    "3306",
					"root",
					"",
					"pw"
            );
			
         /*   IDatabaseConnection dbConn = new PostgreSQLDatabaseConnection(
					"ec2-23-21-161-153.compute-1.amazonaws.com",
                    "5432",
					"ikqepbqiwxmcwe",
					"cI6PNkfjz4SajHnobEeCHwmvfv",
					"dbtooekfdenm82",
					true
            );*/

            DBInfoProcessor dbip = new DBInfoProcessor(dbConn);
			Remote rmt = new Remote();
			rmt.setHost("localhost");
            rmt.setPort("3306");
			rmt.setUser("root");
			rmt.setPassword("");
			rmt.setDatabase("pw");
			
			dbip.buildTreeForRemoteConnectionTreeNode(rmt);
			
            //export scriptul bazei
            //PostgreScriptWriter postgreScriptWriter = new PostgreScriptWriter(tn,"C:\\Users\\Auras\\Desktop\\script.sql");
            //postgreScriptWriter.writeScriptToFile();

//			tn.print(2);
            //Tree tree = new Tree(tn);
			//XMLWriter.writeToFile("C:\\Users\\Auras\\Desktop\\test.xml", rmt);
			//TreeNode readTn = XMLReader.readFromFile("C:\\Users\\Auras\\Desktop\\test.xml");
			//XMLWriter.writeToFile("C:\\Users\\Auras\\Desktop\\test2.xml", readTn);
			System.out.println(rmt.getSqlStatement(SQLEngine.MYSQL));
			
		} catch (Exception e){
			e.printStackTrace();
			AppLogger.getLogger().error("Error", e);
		}
	}
}
