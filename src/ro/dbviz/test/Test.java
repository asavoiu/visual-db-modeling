package ro.dbviz.test;

import ro.dbviz.logging.AppLogger;
import ro.dbviz.sql.mysqli.DatabaseConnection;

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
			dbConnection.getSchema();
		} catch (Exception e) {
			AppLogger.getLogger().error("Error", e);
		}
	}

}
