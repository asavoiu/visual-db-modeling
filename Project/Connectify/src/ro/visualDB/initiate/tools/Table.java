package ro.visualDB.initiate.tools;

import ro.visualDB.initiate._types._TableType;

public class Table {
	String name;
	_TableType type;
	
	public Table(String name, _TableType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public _TableType getType() {
		return type;
	}

	public void setType(_TableType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
