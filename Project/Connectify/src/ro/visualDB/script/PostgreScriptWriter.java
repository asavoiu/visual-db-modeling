package ro.visualDB.script;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ro.visualDB.sql.model.Column;
import ro.visualDB.xml.TreeNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class PostgreScriptWriter {

    private ArrayList<TreeNode> dbName;
    private String filePath;

    public PostgreScriptWriter(TreeNode tn, String filePath) {
        dbName = tn.getChildren();
        this.filePath = filePath;
    }

    public void writeScriptToFile() throws Exception {
        FileWriter fstream = new FileWriter(filePath);
        BufferedWriter myPGSqlFile = new BufferedWriter(fstream);
        //Numele bazei
        System.out.println(dbName.toString());
//        myPGSqlFile.write();
        ArrayList<TreeNode> schemanName = dbName.get(0).getChildren();
        //Numele schemei
        System.out.println(schemanName.toString());
        ArrayList<TreeNode> tableNames = schemanName.get(0).getChildren();
        //Numele tabelelor
        System.out.println(tableNames.toString());

        //Numele coloanelor
        for(TreeNode t:tableNames) {
            System.out.println("nume tabel = "+t.toString());
            ArrayList<TreeNode> coloane = t.getChildren();
            for(TreeNode tCld:coloane){
                System.out.println("\t"+tCld.toString());
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                Element rootElement = tCld.getDomElement(doc);
//                for(int i=0;i<rootElement.getAttributes().getLength();i++){
//                    System.out.println(rootElement.getAttributes().getNamedItem("dataType")+" "+
//                            rootElement.getAttributes().getNamedItem("foreignKey")+" "+
//                            rootElement.getAttributes().getNamedItem("primaryKey")+" "+
//                            rootElement.getAttributes().getNamedItem("isAutoIncrement")+" "+
//                            rootElement.getAttributes().getNamedItem("isNullable")+" "+
//                            rootElement.getAttributes().getNamedItem("name")+" "+
//                            rootElement.getAttributes().getNamedItem("column")
//                    );
//                }
            }
        }
        myPGSqlFile.close();
    }

    private String createDatabase(String dbName, String owner, String encoding, String tablespace, String lcColate,
                                  String lcCtype, int connectionLimit) {
        return "CREATE DATABASE "+dbName+"\n" +
               "  WITH OWNER = "+owner+"\n" +
               "       ENCODING = 'UTF8'\n" +
               "       TABLESPACE = pg_default\n" +
               "       LC_COLLATE = 'en_US.UTF-8'\n" +
               "       LC_CTYPE = 'en_US.UTF-8'\n" +
               "       CONNECTION LIMIT = "+connectionLimit+";\n" +
               "GRANT ALL ON DATABASE "+dbName+" TO "+owner+";";
    }

    private String createSchema(String schemaName, String owner){
        return "CREATE SCHEMA "+schemaName+"\n" +
               "  AUTHORIZATION "+owner+";";
    }

    //TODO treat special cases. Ex: constraints, length, , etc.
    private String createTable(String dbName, String owner, String tableName, ArrayList<Column> tableColumns, boolean oids){
        String myNewTable = "CREATE TABLE "+dbName+"."+tableName+"\n" +
                            "(\n";

        for(Column c:tableColumns){
            myNewTable += "  "+c.getColumnName() + " " + c.getDataType()+"\n";
        }

        myNewTable += ")\n" +
                      "WITH (\n" +
                      "OIDS="+oids+"\n" +
                      ");\n" +
                      "ALTER TABLE " + dbName + "." + tableName + "\n" +
                      "  OWNER TO " + owner+";";

        return myNewTable;
    }

}
