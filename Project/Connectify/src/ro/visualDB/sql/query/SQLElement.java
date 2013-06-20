package ro.visualDB.sql.query;

public interface SQLElement {
	public String getSqlStatement(int sqlEngine) throws Exception;
}
