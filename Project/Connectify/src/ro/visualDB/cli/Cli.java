package ro.visualDB.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ro.visualDB.api.Api;
import ro.visualDB.remotes.Remote;
import ro.visualDB.sql.connection.IDatabaseConnection;
import ro.visualDB.sql.connection.MySQLDatabaseConnection;
import ro.visualDB.sql.connection.PostgreSQLDatabaseConnection;
import ro.visualDB.sql.helpers.DBInfoProcessor;
import ro.visualDB.sql.query.SQLEngine;
import ro.visualDB.xml.TreeNode;
import ro.visualDB.xml.XMLReader;
import ro.visualDB.xml.XMLWriter;

/**
 ** Example usage:
 ** Export DB structure to XML:
 * "java -jar dbvis.jar
 *   -exml C:\Users\Auras\Desktop\Test.xml
 *   -h localhost
 *   -p 3306
 *   -u root
 *   -pw ""
 *   -d pw
 *   -dt mysql"
 *   
 ** Export DB structure from XML to another XML
 * 	 - ixml "C:\input.xml"
 * 	 - exml "C:\output.xml"
 * @author Auras
 * 
 */
public class Cli {
	
	public static Options getOptions(){
		// create Options object
		Options options = new Options();

		// add help option
		options.addOption("help", false, "display help");
		
		// add database connection options
		options.addOption("c", false, "connect to database (Requires -d -h -p -user -passwd)");
		options.addOption("d", true, "remote database to connect to (Used with -c)");
		options.addOption("dt", true, "remote database type: mysql, postgres (Used with -c)");
		options.addOption("h", true, "host for remote database (Used with -c)");
		options.addOption("p", true, "port for remote database (Used with -c)");		
		options.addOption("u", true, "user name for remote database (Used with -c)");
		options.addOption("pw", true, "user password for remote database (Used with -c)");
		options.addOption("ssl", false, "use ssl for remote database (Used with -c)");

		// add xml export options
		options.addOption("exml", true, "export database structure to specific xml file"
									+	"<Requires -c>" );
		
		// add sql script export options
		options.addOption("esql", true, "export database structure to specific sql file "
									+	"<Requires -c>" );
		
		// add import form xml options
		options.addOption("ixml", true, "import database structure from XML file ");
		
		return options;
	}
	
	public static void main(String[] args) {
		//Get available options
		Options options = Cli.getOptions();
		
		//Create the parser
		CommandLineParser parser = new GnuParser();

		try {
			CommandLine cmd = parser.parse(options, args);
			
			//Show help
			if (cmd.hasOption("help")) {
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("<java -jar dbvis.jar> ", options );
				return;
			}
			
			//Export Remote Database structure to SQL 
			if (cmd.hasOption("esql")) {
				TreeNode rootTn = null;
				IDatabaseConnection dbConn = null;
				DBInfoProcessor dbip = null;
				Remote rmt = null;
				if (cmd.hasOption("ixml")){
					// Import Structure from xml file
					rootTn = Api.importFromXML(cmd.getOptionValue("ixml"));
				} else if (cmd.hasOption("d")  &&
						cmd.hasOption("dt") &&
						cmd.hasOption("h")  &&
						cmd.hasOption("p")  &&
						cmd.hasOption("u")  &&
						cmd.hasOption("pw")) {
					rmt = new Remote();
					rmt.setHost(cmd.getOptionValue("h"));
			        rmt.setPort(cmd.getOptionValue("p"));
					rmt.setUser(cmd.getOptionValue("u"));
					rmt.setPassword(cmd.getOptionValue("pw"));
					rmt.setDatabase(cmd.getOptionValue("d"));
					if (cmd.getOptionValue("dt").equals("mysql")) {
						rmt.setDatabaseEngine(SQLEngine.MYSQL);
					} else if (cmd.getOptionValue("dt").equals("postgres")) {
						rmt.setDatabaseEngine(SQLEngine.POSTGRES);
					}
					if (cmd.hasOption("ssl")) {
						rmt.setSsl(true);
					}
					Api.importFromRemote(rmt);
				} else {
					System.out.println("Parameters missing. Please verify usage!");
					return;
				}
				System.out.println("Export SQL to FILE:" + cmd.getOptionValue("esql"));
				// User the rooTn to export
				if (cmd.getOptionValue("dt").equals("mysql")) {
					// TODO
					// Generate mysql script
				} else {
					// TODO
					// Generate postgre script
				}
			}
			
			//Export Remote Database structure to XML
			if (cmd.hasOption("exml")) {
				TreeNode rootTn = null;
				IDatabaseConnection dbConn = null;
				DBInfoProcessor dbip = null;
				Remote rmt = null;
				if (cmd.hasOption("ixml")){
					// Import Structure from xml file
					rootTn = Api.importFromXML(cmd.getOptionValue("ixml"));
				} else if (cmd.hasOption("d")  &&
						cmd.hasOption("dt") &&
						cmd.hasOption("h")  &&
						cmd.hasOption("p")  &&
						cmd.hasOption("u")  &&
						cmd.hasOption("pw")) {
					rmt = new Remote();
					rmt.setHost(cmd.getOptionValue("h"));
		            rmt.setPort(cmd.getOptionValue("p"));
					rmt.setUser(cmd.getOptionValue("u"));
					rmt.setPassword(cmd.getOptionValue("pw"));
					rmt.setDatabase(cmd.getOptionValue("d"));
					if (cmd.getOptionValue("dt").equals("mysql")) {
						rmt.setDatabaseEngine(SQLEngine.MYSQL);
					} else if (cmd.getOptionValue("dt").equals("postgres")) {
						rmt.setDatabaseEngine(SQLEngine.POSTGRES);
					}
					if (cmd.hasOption("ssl")) {
						rmt.setSsl(true);
					}
					Api.importFromRemote(rmt);						
				} else {
					System.out.println("Parameters missing. Please verify usage!");
					return;					
				}
				System.out.println("Export XML to FILE:" + cmd.getOptionValue("exml"));
				Api.exportToXML(rmt, cmd.getOptionValue("exml"));
			}
		} catch (ParseException ex) {
			System.out.println("ERROR:" + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR:" + e.getMessage());
		}	
	}

}
