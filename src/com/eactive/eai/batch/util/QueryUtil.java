package com.eactive.eai.batch.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

public class QueryUtil {
	public static String createSQL(String tableOwner, String tableName, String[] columns) {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("INSERT INTO "+ tableOwner + tableName + " (\n");
    	for(int i=0; i<columns.length; i++) {
    		sql.append(columns[i]);
    		if(i < (columns.length-1)) {
    			sql.append(", ");
    		}
    	}
    	sql.append("\n ) VALUES ( \n"); 
    	for(int i=0; i<columns.length; i++) {
    		sql.append("?");
    		if(i < (columns.length-1)) {
    			sql.append(", ");
    		}
    	}
    	sql.append("\n ) "); 
    	
    	return sql.toString();
    	
    	
    }
	
	public static String createCheckSQL(String tableOwner, String tableName) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select count(*) from "+ tableOwner + tableName);
    	return sql.toString();    	
    }
	
	public static String createSelectSQL(String tableOwner, String tableName) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select * from "+ tableOwner + tableName);
    	return sql.toString();    	
    }
	
	public static String createSelectSQL(String tableOwner, String tableName, String col, String whe, String order) {
		StringBuffer sql = new StringBuffer();
		sql.append("select " + col + " from " + tableOwner + tableName);
		if (StringUtils.isNotBlank(whe)) {
			sql.append(" where " + whe);
		}
		if (StringUtils.isNotBlank(order)) {
			sql.append(" order by " + order);
		}
		return sql.toString();
	}
	
	public static String createSelectEmptySQL(String tableOwner, String tableName) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("select * from "+ tableOwner + tableName + " WHERE 1=2");
    	return sql.toString();    	
    }
	
	public static String createInsertSQL(String tableOwner, String tableName, String[] columns) {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("INSERT INTO "+ tableOwner + tableName + " (\n");
    	for(int i=0; i<columns.length; i++) {
    		sql.append(columns[i]);
    		if(i < (columns.length-1)) {
    			sql.append(", ");
    		}
    	}
    	sql.append("\n ) VALUES ( \n"); 
    	for(int i=0; i<columns.length; i++) {
    		sql.append("?");
    		if(i < (columns.length-1)) {
    			sql.append(", ");
    		}
    	}
    	sql.append("\n ) "); 
    	return sql.toString();
    }
	
	public static String deleteSQL(String tableOwner, String tableName, String[] columns) {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("DELETE FROM "+ tableOwner + tableName + " WHERE ");
    	for(int i=0; i<columns.length; i++) {
    		sql.append(columns[i]).append(" = ? ");
    		if(i < (columns.length-1)) {
    			sql.append(" AND ");
    		}
    	}
    	return sql.toString();
    }
	
	public static String truncateSQL(String tableOwner, String tableName) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("truncate table "+ tableOwner + tableName);
//    	sql.append("delete from "+ tableOwner + tableName);
    	return sql.toString();
    }
	
	public static String getOdaTableName(String servicepath) {
		if(servicepath == null) {
			return servicepath; 
		}
		else {
			String[] splited = servicepath.split("\\.");
			return splited[0].trim();
		}
	}
	
	public static String getAdgTableName(String servicepath) {
		String tableName = null; 
		if(servicepath != null) {
			tableName = servicepath.trim();
			
			if(tableName.contains("/")) {
				int pos = tableName.lastIndexOf("/");
				tableName = tableName.substring(pos +1);
			}
			
			if(tableName.startsWith("get")) {
				tableName = tableName.substring(3).toLowerCase();
			}
			return tableName;
		}
		else {
			return servicepath;
		}
	}
	
	public static String getTableName(String servicepath) {
		String tableName = null; 
		if(servicepath != null) {
			tableName = servicepath.trim();
			
			if(tableName.contains("/")) {
				int pos = tableName.lastIndexOf("/");
				tableName = tableName.substring(pos +1);
			}
			
			if(tableName.contains(".")) {
				return getOdaTableName(tableName);
			}
			else if(tableName.startsWith("get")) {
				return getAdgTableName(tableName);
			}
			return tableName;
		}
		else {
			return servicepath;
		}
	}
	
	public static String getSqlDropTable(String tableOwner, String tableName, boolean withEndCommand) {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("DROP TABLE ");
    	
    	if(tableOwner !=null && tableOwner.trim().length() > 0) {
	    	sql.append(tableOwner);
	    	if(!tableOwner.endsWith(".")) {
	    		sql.append(".");
	    	}
    	}
    	sql.append(tableName);
    	if(withEndCommand) {
    		sql.append(";");
		}
    	return sql.toString();
    }
	
	public static String getSqlColumnComment(String tableOwner, String tableName, 
			String columnName, String comment, boolean withEndCommand) {
    	StringBuffer sql = new StringBuffer();
    	// COMMENT ON COLUMN APITABLE.TABLENAME IS '테이블명';
    	sql.append("COMMENT ON COLUMN ");
    	
    	if(tableOwner !=null && tableOwner.trim().length() > 0) {
	    	sql.append(tableOwner);
	    	if(!tableOwner.endsWith(".")) {
	    		sql.append(".");
	    	}
    	}
    	sql.append(tableName);
    	sql.append(".");
    	sql.append(columnName);
    	sql.append(" IS '");
    	sql.append(comment);
    	sql.append("'");
    	if(withEndCommand) {
    		sql.append(";");
		}
    	return sql.toString();
    }
	
	public static String getSqlCreateString(String tableName, 
			LinkedHashMap<String, Column> colMap, PrimaryKey pk, boolean withEndCommand) {
		return getSqlCreateString(null, tableName, colMap, pk, withEndCommand);
	}
	
	public static String getSqlCreateString(String ownerName, String tableName, 
		LinkedHashMap<String, Column> colMap, PrimaryKey pk, boolean withEndCommand) {
		StringBuffer buf = new StringBuffer();
		String ownerTableName = null;
		
		if(ownerName !=null && ownerName.trim().length() > 0) {
			ownerTableName = ownerName + "." + tableName;
		}
		else {
			ownerTableName = tableName;
		}
		
		buf.append("CREATE TABLE ").append(ownerTableName).append(" (\n");
		boolean isFirst = true;
		for(Iterator<Column> linkitr = colMap.values().iterator(); linkitr.hasNext(); ) {
			Column col = linkitr.next();
			if ( isFirst ) {
				isFirst = false;
				buf.append( "\t  " );
			}
			else {
				buf.append( "\t, " );
			}
			buf.append(col.getName());
			buf.append(" ");
			buf.append(col.getTypeString());
			String defaultValue = col.getDefaultValue();
			if ( defaultValue != null ) {
				buf.append( " default " ).append( defaultValue );
			}

			if ( !col.isNullable() ) {
				buf.append( " not null" );
			}
			buf.append( "\n" );
		}
		
		if(pk != null) {
			buf.append("\t, CONSTRAINT ").append("pk_").append(tableName.toLowerCase()).append(" PRIMARY KEY (");
			buf.append(pk.toStringCommaDelimeter());
			buf.append(")\n");
		}
		
		if(withEndCommand) {
			buf.append(");");
		}
		else {
			buf.append(")");
		}
		
		return buf.toString();

	}
	
	// create table script 생성에 필요한 inner classes
	public static enum SQLType {
		BOOLEAN,VARCHAR,VARCHAR2,CHAR,NUMBER,BLOB,CLOB,DATE,TIMESTAMP
	}
	
	public static class Column {
		public static HashMap<String, String> typeStringMap = new HashMap<>();
		static {
			typeStringMap.put(SQLType.BOOLEAN.toString(), "BOOLEAN");
			typeStringMap.put(SQLType.VARCHAR.toString(), "VARCHAR($l)");
			typeStringMap.put(SQLType.VARCHAR2.toString(), "VARCHAR2($l)");
			typeStringMap.put(SQLType.CHAR.toString(), "CHAR($l)");
			typeStringMap.put(SQLType.NUMBER.toString(), "NUMBER($p,$s)");
			typeStringMap.put(SQLType.BLOB.toString(), "BLOB");
			typeStringMap.put(SQLType.CLOB.toString(), "CLOB");
			typeStringMap.put(SQLType.DATE.toString(), "DATE");
			typeStringMap.put(SQLType.TIMESTAMP.toString(), "TIMESTAMP");
			// 타입추가 필요시 추가

		}
		public static final int DEFAULT_LENGTH = 255;
		public static final int DEFAULT_PRECISION = 19;
		public static final int DEFAULT_SCALE = 2;

		private String name;
		private SQLType sqlType;
		private int length = DEFAULT_LENGTH;// 소수점있는 숫자를 제외한 숫자형과 문자형의 길이
		private int scale = DEFAULT_SCALE;// 소수점 자릿수
		private int precision = DEFAULT_PRECISION;// 소수점을 포함한 전체 자릿수
		private boolean nullable = true;
		private boolean unique;
		private String defaultValue;
		private String comment;

		public Column(String name, SQLType sqlType) {
			this.name = name;
			this.sqlType = sqlType;
		}

		public Column(String name, SQLType sqlType, int length) {
			this.name = name;
			this.sqlType = sqlType;
			this.length = length;
		}
		
		public Column(String name, SQLType sqlType, int length, boolean nullable) {
			this.name = name;
			this.sqlType = sqlType;
			this.length = length;
			this.nullable = nullable;
		}
		
		public Column(String name, SQLType sqlType, int precision, int scale) {
			this.name = name;
			this.sqlType = sqlType;
			this.precision = precision;
			this.scale = scale;			
		}
		public Column(String name, SQLType sqlType, int precision, int scale, boolean nullable) {
			this.name = name;
			this.sqlType = sqlType;
			this.precision = precision;
			this.scale = scale;
			this.nullable = nullable;
		}
		
		public String getTypeString() {
			String typeStringPattern = typeStringMap.get(sqlType.toString());
			typeStringPattern = StringUtils.replaceOnce(typeStringPattern, "$s", Integer.toString(scale));
			typeStringPattern = StringUtils.replaceOnce(typeStringPattern, "$l", Long.toString(length));
			return StringUtils.replaceOnce(typeStringPattern, "$p", Integer.toString(precision));
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public SQLType getSqlType() {
			return sqlType;
		}

		public void setSqlType(SQLType sqlType) {
			this.sqlType = sqlType;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public int getScale() {
			return scale;
		}

		public void setScale(int scale) {
			this.scale = scale;
		}

		public int getPrecision() {
			return precision;
		}

		public void setPrecision(int precision) {
			this.precision = precision;
		}

		public boolean isNullable() {
			return nullable;
		}

		public void setNullable(boolean nullable) {
			this.nullable = nullable;
		}

		public boolean isUnique() {
			return unique;
		}

		public void setUnique(boolean unique) {
			this.unique = unique;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public static int getDefaultLength() {
			return DEFAULT_LENGTH;
		}

		public static int getDefaultPrecision() {
			return DEFAULT_PRECISION;
		}

		public static int getDefaultScale() {
			return DEFAULT_SCALE;
		}

		public boolean equals(Column column) {
			if (null == column) {
				return false;
			}
			if (this == column) {
				return true;
			}
			return isQuoted(column.name) ? name.equals(column.name) : name.equalsIgnoreCase(column.name);
		}

		public static boolean isQuoted(String name) {
			return (name.startsWith("`") && name.endsWith("`")) 
					|| (name.startsWith("[") && name.endsWith("]"))
					|| (name.startsWith("\"") && name.endsWith("\""));
		}

		@Override
		public String toString() {
			return "Column [name=" + name + ", sqlType=" + sqlType + ", length=" + length + ", scale=" + scale
					+ ", precision=" + precision + ", nullable=" + nullable + ", unique=" + unique + ", defaultValue="
					+ defaultValue + ", comment=" + comment + "]";
		}

	}
	
	public static class PrimaryKey {
		ArrayList<Column> columns = new ArrayList<>();

		public void addColumn(Column column) {
			column.setNullable(false);
			if (!columns.contains(column)) {
				columns.add(column);
			}
		}

		public String toStringCommaDelimeter() {
			String commaString = "";
			boolean isFirst = true;
			for (Column col : columns) {
				if (isFirst)
					isFirst = false;
				else
					commaString += ", ";

				commaString += col.getName();
			}
			return commaString;
		}
	}
	
    public static void main(String[] argv) {
    	String servicepath = "company.json";
    	
    	String tableName = getTableName(servicepath);
    	System.out.println("tableName = " + tableName);
    	
    	servicepath = "company";
    	tableName = getTableName(servicepath);
    	System.out.println("tableName = " + tableName);
    	
    	servicepath = null;
    	tableName = getTableName(servicepath);
    	System.out.println("tableName = " + tableName);
    	
    	
    	servicepath = "getCorpOutline";
    	tableName = getTableName(servicepath);
    	System.out.println("tableName = " + tableName);
    	
    	servicepath = "GetCorpBasicInfoService/getCorpOutline";
    	tableName = getTableName(servicepath);
    	System.out.println("tableName = " + tableName);
    	
    	
//    	
//    	String[] columns =  new String[18];
//        columns[0 ] = "status"        ;
//        columns[1 ] = "message"       ;
//        columns[2 ] = "corp_name"     ;
//        columns[3 ] = "corp_name_eng" ;
//        columns[4 ] = "stock_name"    ;
//        columns[5 ] = "stock_code"    ;
//        columns[6 ] = "ceo_nm"        ;
//        columns[7 ] = "corp_cls"      ;
//        columns[8 ] = "jurir_no"      ;
//        columns[9 ] = "bizr_no"       ;
//        columns[10] = "adres"         ;
//        columns[11] = "hm_url"        ;
//        columns[12] = "ir_url"        ;
//        columns[13] = "phn_no"        ;
//        columns[14] = "fax_no"        ;
//        columns[15] = "induty_code"   ;
//        columns[16] = "est_dt"        ;
//        columns[17] = "acc_mt"        ;
//        
    	String[] columns =  new String[1];
    	columns[0 ] = "corp_cpde"     ;

    	System.out.println(createSQL("eai.", tableName, columns));
        System.out.println(deleteSQL("eai.", tableName, columns));
        
        // create table 생성 테스트
        Column empno = new Column("empno", SQLType.NUMBER, 10, 0);
		empno.setComment("사번");
		Column name = new Column("name", SQLType.VARCHAR, 50);
		name.setComment("성명");
		Column info = new Column("info", SQLType.VARCHAR2, 4000, false);
		info.setComment("참고정보");
		Column age = new Column("age", SQLType.NUMBER, 3, 0);
		age.setComment("나이");
		Column photo = new Column("photo", SQLType.BLOB);
		photo.setComment("사진");
		LinkedHashMap<String, Column> colMap = new LinkedHashMap<>(); // 모든 컬럼들을 추가한다. 컬럼 순차 생성을 위해서는 LinkedHashMap을 사용한다.
		colMap.put(empno.getName(), empno);
		colMap.put(name.getName(), name);
		colMap.put(info.getName(), info);
		colMap.put(age.getName(), age);
		colMap.put(photo.getName(), photo);
		PrimaryKey pk = new PrimaryKey();//위에서 생성한 Column 객체를 PK용 컬럼으로 추가한다.
		pk.addColumn(empno);
		pk.addColumn(name);
		System.out.println(getSqlCreateString("temp_emp", colMap, pk, true));
		System.out.println(getSqlCreateString("eai", "temp_emp", colMap, pk, false));
		
		System.out.println(getSqlColumnComment("eai", "temp_emp", empno.getName(), empno.getComment(), false));
		System.out.println(getSqlColumnComment("eai", "temp_emp", name.getName(), name.getComment(), false));
		
		System.out.println(getSqlDropTable("eai.", "temp_emp", true));
		System.out.println(getSqlDropTable("eai", "temp_emp", false));
    }
}
