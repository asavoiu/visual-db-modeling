package ro.visualDB.script;

import ro.visualDB.sql.model.Column;
import ro.visualDB.sql.model.ForeignKeys;

import java.util.ArrayList;

public class PostrgreSQLScriptWriter {

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

    private String createTable(String dbName, String  owner, String tableName, ArrayList<Column> tableColumns, boolean oids){
        String myNewTable = "CREATE TABLE "+dbName+"."+tableName+"\n" +
                "(\n";
        int i=0;
        for(Column c:tableColumns){
            myNewTable += "  "+c.getColumnName() + " " + c.getDataType();


            if(c.getColumnLength()!=null){
                myNewTable += " (" + c.getColumnLength().toString() + "," + c.getColumnPrecision() + ")";
            }

            if(c.getNullable()!=0){
                myNewTable += " NOT NULL";
            }

            if(c.isPrimaryKey()){
                myNewTable += ",\n" + "CONSTRAINT " + tableName + "_pkey PRIMARY KEY (" + c.getColumnName() + ")";
            }

            if(c.isForeignKey()){
                ForeignKeys fks = c.getForeignKeys();
                myNewTable += ",\n" + "CONSTRAINT " +
                        fks.getPrimaryKeyTableName() + "_" + fks.getForeignKeyTableName() +
                        " FOREIGN KEY (" + fks.getForeignKeyColumnName() + ") REFERENCES " +
                        dbName + "." + fks.getPrimaryKeyTableName() + "(" + fks.getPrimaryKeyColumnName() + ") " +
                        "MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION \n";
            }

            if(i!=(tableColumns.size()-1)){
                myNewTable += ",";
            }
            myNewTable += "\n";
            i++;

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
