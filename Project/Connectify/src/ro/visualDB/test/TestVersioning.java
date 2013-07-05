package ro.visualDB.test;

import ro.visualDB.api.Api;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.model.Catalog;
import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.Schema;
import ro.visualDB.sql.model.Table;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.versioning.Versioning;
import ro.visualDB.xml.TreeNode;

public class TestVersioning {

	public static void main(String[] args) {
		try {
			Remote rmt = new Remote();
			rmt.setHost("ec2-23-21-161-153.compute-1.amazonaws.com");
            rmt.setPort("5432");
			rmt.setUser("ikqepbqiwxmcwe");
			rmt.setPassword("cI6PNkfjz4SajHnobEeCHwmvfv");
			rmt.setDatabase("dbtooekfdenm82");
			rmt.setDatabaseEngine(SQLEngine.POSTGRES);
			
			Api.importFromRemote(rmt);
			
			//Write to file
			Api.exportToXML(rmt, "test.xml");
			TreeNode readTn = Api.importFromXML("test.xml");
			
			Catalog ct = (Catalog)readTn.getChildAt(0); // dbtooekfdenm82
			Schema s = (Schema) ct.getChildAt(0); // dbtooekfdenm82
			Table test = (Table) s.getChildAt(5); // games
			Column id = (Column) test.getChildAt(0);// id_games
			
			id.setAltered(true);
			
			System.out.println("Modified column " + id + " from table " +
							test + " : schema " + s + " catalog : ct " +
							" TO NOT FOREIGN_KEY");
			Api.exportToXML(readTn, "test2.xml");
			
			TreeNode tn = Versioning.getTreeWithFlags("test.xml", "test2.xml");
			System.out.println("Differences found by Versioning Module:\n\t\t" + Versioning.differences(tn));
		} catch (Exception e) {
			
		}
	}
}
