package ro.visualDB.sql.helpers;

import ro.visualDB.sql.query.SQLEngine;

public class SQLTypeConverter {

	private static String convertMySQLToPostgreSQL(String type) {
		// integers
		if (type.equalsIgnoreCase("TINYINT") ||
				type.equalsIgnoreCase("SMALLINT")) {
			return "SMALLINT";
		}		
		if (type.equalsIgnoreCase("MEDIUMINT") ||
				type.equalsIgnoreCase("INT") ||
				type.equalsIgnoreCase("INTEGER")) {
			return "INTEGER";
		}		
		if (type.equalsIgnoreCase("BIGINT")) {
			return "BIGINT";
		}
		
		// Unsigned integers
		if (type.equalsIgnoreCase("TINYINT UNSIGNED")) {
			return "SMALLINT";
		}
		if (type.equalsIgnoreCase("SMALLINT UNSIGNED") ||
				type.equalsIgnoreCase("MEDIUMINT UNSIGNED")) {
			return "INTEGER";
		}
		if (type.equalsIgnoreCase("INT UNSIGNED") ||
				type.equalsIgnoreCase("INTEGER UNSIGNED")) {
			return "BIGINT";
		}
		if (type.equalsIgnoreCase("BIGINT UNSIGNED")) {
			return "NUMERIC(20)";
		}
		if (type.equalsIgnoreCase("NUMERIC")) {
			return "NUMERIC";
		}
		if (type.equalsIgnoreCase("DECIMAL")) {
			return "DECIMAL";
		}
		
		// floating points
		if (type.equalsIgnoreCase("FLOAT")) {
			return "REAL";
		}
		if (type.equalsIgnoreCase("FLOAT UNSIGNED")) {
			return "REAL";
		}
		if (type.equalsIgnoreCase("DOUBLE")) {
			return "DOUBLE PRECISION";
		}
		
		// boolean
		if (type.equalsIgnoreCase("BOOLEAN")) {
			return "BOOLEAN";
		}
		
		// text
		if (type.equalsIgnoreCase("TINYTEXT")) {
			return "TEXT";
		}
		if (type.equalsIgnoreCase("TEXT")) {
			return "TEXT";
		}
		if (type.equalsIgnoreCase("MEDIUMTEXT")) {
			return "TEXT";
		}
		if (type.equalsIgnoreCase("LONGTEXT")) {
			return "TEXT";
		}
		if (type.equalsIgnoreCase("VARCHAR")) {
			return "VARCHAR";
		}
		if (type.equalsIgnoreCase("CHAR")) {
			return "CHAR";
		}
		
		// binary/blobs/byte arrays
		if (type.equalsIgnoreCase("BINARY")) {
			return "BYTEA";
		}
		if (type.equalsIgnoreCase("VARBINARY")) {
			return "BYTEA";
		}
		if (type.equalsIgnoreCase("TINYBLOB")) {
			return "BYTEA";
		}
		if (type.equalsIgnoreCase("BLOB")) {
			return "BYTEA";
		}
		if (type.equalsIgnoreCase("MEDIUMBLOB")) {
			return "BYTEA";
		}
		if (type.equalsIgnoreCase("LONGBLOB")) {
			return "BYTEA";
		}
		
		// date/timestamp
		if (type.equalsIgnoreCase("DATE")) {
			return "DATE";
		}
		if (type.equalsIgnoreCase("TIME")) {
			return "TIME";
		}
		if (type.equalsIgnoreCase("DATETIME")) {
			return "TIMESTAMP";
		}
		if (type.equalsIgnoreCase("TIMESTAMP")) {
			return "TIMESTAMP";
		}
		
		// serial
		if (type.equalsIgnoreCase("SERIAL")) {
			return "SERIAL";
		}
		if (type.equalsIgnoreCase("INT DEFAULT SERIAL")) {
			return "SERIAL";
		}
		if (type.equalsIgnoreCase("BIGINT UNSIGNED NOT NULL AUTO_INCREMENT UNIQUE")) {
			return "SERIAL";
		}
		if (type.equalsIgnoreCase("INT NOT NULL AUTO_INCREMENT UNIQUE")) {
			return "SERIAL";
		}
		
		// TODO ENUM
		
		return type;
	}
	
	private static String convertPostgreSQLToMySQL(String type) {
		// integers
		if (type.equalsIgnoreCase("SMALLINT")) {
			return "SMALLINT";
		}		
		if (type.equalsIgnoreCase("INTEGER")) {
			return "INTEGER";
		}		
		if (type.equalsIgnoreCase("BIGINT")) {
			return "BIGINT";
		}
		
		// numeric
		if (type.equalsIgnoreCase("NUMERIC")) {
			return "NUMERIC";
		}
		if (type.equalsIgnoreCase("DECIMAL")) {
			return "DECIMAL";
		}
		
		// floating points
		if (type.equalsIgnoreCase("REAL")) {
			return "FLOAT UNSIGNED";
		}
		if (type.equalsIgnoreCase("DOUBLE PRECISION")) {
			return "DOUBLE";
		}
		
		// boolean
		if (type.equalsIgnoreCase("BOOLEAN")) {
			return "BOOLEAN";
		}
		
		// text
		if (type.equalsIgnoreCase("TEXT")) {
			return "TEXT";
		}
		if (type.equalsIgnoreCase("VARCHAR") ||
				type.equalsIgnoreCase("CHARACTER VARYING")) {
			return "VARCHAR";
		}
		if (type.equalsIgnoreCase("CHAR") ||
				type.equalsIgnoreCase("CHARACTER")) {
			return "CHAR";
		}
		
		
		// binary strings/ blobs
		if (type.equalsIgnoreCase("BYTEA")) {
			return "VARBINARY";
		}
		
		// date/timestamp
		if (type.equalsIgnoreCase("DATE")) {
			return "DATE";
		}
		if (type.equalsIgnoreCase("TIME")) {
			return "TIME";
		}
		if (type.equalsIgnoreCase("TIMESTAMP")) {
			return "TIMESTAMP";
		}
		
		// serial
		if (type.equalsIgnoreCase("SERIAL")) {
			return "SERIAL";
		}
		
		// TODO ENUM
		
		return type;
	}


	public static String convertType(String type, int fromEngine, int toEngine) {
		if (fromEngine == toEngine) {
			return type;
		}
		switch (fromEngine) {
			case SQLEngine.MYSQL:
				return convertMySQLToPostgreSQL(type);
			case SQLEngine.POSTGRES:
				return convertPostgreSQLToMySQL(type);
			default:
				break;
		}
		return null;
	}
}
