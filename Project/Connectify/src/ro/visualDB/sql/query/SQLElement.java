package ro.visualDB.sql.query;

public interface SQLElement {
	public String getSqlStatement(int sqlEngine) throws Exception;
	public String getAlterSqlStatement(int sqlEngine) throws Exception;
	public String getChangeSqlStatement(int sqlEngine) throws Exception;
	public String getModifySqlStatement(int sqlEngine) throws Exception;

	public void setAltered(boolean altered);
	public boolean isAltered();
	public void setChanged(boolean changed);
	public boolean isChanged();
	public void setModified(boolean modified);
	public boolean isModified();
}
