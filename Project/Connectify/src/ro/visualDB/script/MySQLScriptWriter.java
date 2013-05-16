package ro.visualDB.script;

import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.ForeignKeys;

import java.util.ArrayList;

public class MySQLScriptWriter {


    public class PostrgreSQLScriptWriter {

        private String createDatabase(String dbName, String owner, String encoding, String tablespace, String lcColate,
                                      String lcCtype, int connectionLimit) {

            return "CREATE DATABASE " + dbName + " DEFAULT CHARACTER SET " + encoding + " DEFAULT COLLATE " + lcColate + "; \n" +
                    " grant CREATE,INSERT,DELETE,UPDATE,SELECT on " + dbName + ".* to " + owner + ";\n";
        }

        private String createTable(String dbName, String  owner, String tableName, ArrayList<Column> tableColumns, boolean oids){
            String myNewTable = "CREATE TABLE "+dbName+"."+tableName+"\n" +
                    "(\n";
            int i=0;
            for(Column c:tableColumns){
                myNewTable += "  "+c.getColumnName() + " " + c.getDataType();


                if(c.getColumnLength()!=null){
                    myNewTable += " (" + c.getColumnLength().toString() + ")";
                }

                if(c.getNullable()!=0){
                    myNewTable += " NOT NULL AUTO_INCREMENT";
                }

                if(c.isPrimaryKey()){
                    myNewTable += " PRIMARY KEY \n";
                }

                if(c.isForeignKey()){
                    ForeignKeys fks = c.getForeignKeys();
                    myNewTable += ",\n" + "CONSTRAINT FOREIGN KEY" +
                            fks.getPrimaryKeyTableName() + "_" + fks.getForeignKeyTableName() +
                            fks.getForeignKeyColumnName() + ") REFERENCES " +
                            dbName + "." + fks.getPrimaryKeyTableName() + "(" + fks.getPrimaryKeyColumnName() + ") " +
                            "MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION \n";
                }

                if(i!=(tableColumns.size()-1)){
                    myNewTable += ",";
                }
                myNewTable += "\n";
                i++;

            }

            return myNewTable;
        }

    }


}
