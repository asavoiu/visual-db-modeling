package ro.dbviz.sql;

import ro.dbviz.sql.types.TableType;

public class Table {
	String name;
	TableType type;
	
	public Table(String name, TableType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TableType getType() {
		return type;
	}

	public void setType(TableType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
