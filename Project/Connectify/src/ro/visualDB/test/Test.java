package ro.visualDB.test;

import ro.visualDB.api.Api;
import ro.visualDB.logging.AppLogger;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.model.Catalog;
import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.Table;
import ro.visualDB.sql.query.SQLEngine;

public class Test {

	public static void main(String[] args) {
		AppLogger.getLogger().info("Start Test");
		try{
			// localhost
			Remote rmt1 = new Remote();
			rmt1.setHost("localhost");
            rmt1.setPort("3306");
			rmt1.setUser("root");
			rmt1.setPassword("");
			rmt1.setDatabase("pw");
			rmt1.setDatabaseEngine(SQLEngine.MYSQL);
			
			// postgres
			Remote rmt2 = new Remote();
			rmt2.setHost("ec2-23-21-161-153.compute-1.amazonaws.com");
            rmt2.setPort("5432");
			rmt2.setUser("ikqepbqiwxmcwe");
			rmt2.setPassword("cI6PNkfjz4SajHnobEeCHwmvfv");
			rmt2.setDatabase("dbtooekfdenm82");
			rmt2.setDatabaseEngine(SQLEngine.POSTGRES);
			
			//mysql
			Remote rmt3 = new Remote();
			rmt3.setHost("sql3.freemysqlhosting.net");
            rmt3.setPort("3306");
			rmt3.setUser("sql313131");
			rmt3.setPassword("yB4%pP8%");
			rmt3.setDatabase("sql313131");
			rmt3.setDatabaseEngine(SQLEngine.MYSQL);
			
			Api.importFromRemote(rmt2);
			//System.out.println(Api.getCreateSQLScriptsOfTreeNodeAndChildren(rmt2, SQLEngine.POSTGRES));

			Table t = Api.findTable(rmt2, "dbtooekfdenm82", "dbtooekfdenm82", "galaxies");
			System.out.println("TABLE " + t.getTableName());
			
			Api.executeQueryOnDatabase(rmt3, rmt3.getDatabase(), t.getCreateSqlStatement(SQLEngine.MYSQL));
			Api.importFromRemote(rmt3);
			System.out.println(Api.getCreateSQLScriptsOfTreeNodeAndChildren(rmt3, SQLEngine.MYSQL));
			
			//Api.exportToXML(rmt, "C:\\Users\\Auras\\Desktop\\test.xml");
			//Remote rmtt = (Remote)Api.importFromXML("C:\\Users\\Auras\\Desktop\\test.xml");
            //export scriptul bazei
            //PostgreScriptWriter postgreScriptWriter = new PostgreScriptWriter(tn,"C:\\Users\\Auras\\Desktop\\script.sql");
            //postgreScriptWriter.writeScriptToFile();

//			tn.print(2);
            //Tree tree = new Tree(tn);
			//XMLWriter.writeToFile("C:\\Users\\Auras\\ Desktop\\test.xml", rmt);
			//TreeNode readTn = XMLReader.readFromFile("C:\\Users\\Auras\\Desktop\\test.xml");
			//XMLWriter.writeToFile("C:\\Users\\Auras\\Desktop\\test2.xml", readTn);
			//System.out.println(rmt.getSqlStatement(SQLEngine.MYSQL));
			/*Catalog c = (Catalog) rmt.getChildAt(6);
			Table test = (Table) c.getChildAt(0);
			Column cv = (Column) test.getChildAt(1);
			cv.setColumnSize(200);
			cv.setAltered(true);
			Column id = (Column) test.getChildAt(0);
			id.setColumnSize(9);
			//id.setTypeName("VARCHAR");
			id.setAltered(true);
			//System.out.println(test.getTableCatalogName() + " " + test.getTableSchemaName());
			//System.out.println(test.getModifySqlStatement(SQLEngine.MYSQL));
			//System.out.println(test.getModifySqlStatement(SQLEngine.POSTGRES));
			*/
			//TreeNode tn = Versioning.getTreeWithFlags("C:\\Users\\Auras\\Desktop\\test.xml", "C:\\Users\\Auras\\Desktop\\test2.xml");
			//System.out.println(Versioning.differences(tn));
		} catch (Exception e){
			e.printStackTrace();
			AppLogger.getLogger().error("Error", e);
		}
	}
}
