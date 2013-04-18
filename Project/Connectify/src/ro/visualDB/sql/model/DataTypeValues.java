package ro.visualDB.sql.model;


import java.util.HashMap;
import java.util.Map;

public class DataTypeValues {

    private HashMap<Integer,String> dataTypes=new HashMap<Integer, String>();

    public DataTypeValues(){
        dataTypes.put(new Integer(2003), "ARRAY");
        dataTypes.put(new Integer(-5), "BIGINT");
        dataTypes.put(new Integer(-2), "BINARY");
        dataTypes.put(new Integer(-7), "BIT");
        dataTypes.put(new Integer(2004), "BLOB");
        dataTypes.put(new Integer(16), "BOOLEAN");
        dataTypes.put(new Integer(1), "CHAR");
        dataTypes.put(new Integer(2005), "CLOB");
        dataTypes.put(new Integer(70), "DATALINK");
        dataTypes.put(new Integer(91), "DATE");
        dataTypes.put(new Integer(3), "DECIMAL");
        dataTypes.put(new Integer(2001), "DISTINCT");
        dataTypes.put(new Integer(8), "DOUBLE");
        dataTypes.put(new Integer(6), "FLOAT");
        dataTypes.put(new Integer(4), "INTEGER");
        dataTypes.put(new Integer(2000), "JAVA_OBJECT");
        dataTypes.put(new Integer(-4), "LONGVARBINARY");
        dataTypes.put(new Integer(-1), "LONGVARCHAR");
        dataTypes.put(new Integer(0), "NULL");
        dataTypes.put(new Integer(2), "NUMERIC");
        dataTypes.put(new Integer(1111), "OTHER");
        dataTypes.put(new Integer(7), "REAL");
        dataTypes.put(new Integer(2006), "REF");
        dataTypes.put(new Integer(5), "SMALLINT");
        dataTypes.put(new Integer(2002), "STRUCT");
        dataTypes.put(new Integer(92), "TIME");
        dataTypes.put(new Integer(93), "TIMESTAMP");
        dataTypes.put(new Integer(-6), "TINYINT");
        dataTypes.put(new Integer(-3), "VARBINARY");
        dataTypes.put(new Integer(12), "VARCHAR");
    }

    public HashMap<Integer, String> getDataTypes() {
        return dataTypes;
    }
}
