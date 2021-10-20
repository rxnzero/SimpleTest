package com.dhlee.jdbc;

public class ColumnInfo {
	private String columnName;
	private String dataTypeName;
	private int columnSize;
	private boolean nullable;
	private boolean isPrimaryKey;
	
	public ColumnInfo() {

	}

	public ColumnInfo(String columnName, String dataTypeName, int columnSize, boolean nullable,
			boolean isPrimaryKey) {
		super();
		this.columnName = columnName;
		this.dataTypeName = dataTypeName;
		this.columnSize = columnSize;
		this.nullable = nullable;
		this.isPrimaryKey = isPrimaryKey;
	}
	
	public ColumnInfo(String columnName, String dataTypeName, int columnSize, boolean nullable) {
		super();
		this.columnName = columnName;
		this.dataTypeName = dataTypeName;
		this.columnSize = columnSize;
		this.nullable = nullable;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	@Override
	public String toString() {
		return "ColumnInfo [columnName=" + columnName + ", dataTypeName=" + dataTypeName + ", columnSize=" + columnSize
				+ ", nullable=" + nullable + ", isPrimaryKey=" + isPrimaryKey + "]";
	}
	
}
