package com.eactive.eai.batch.util;

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
//        System.out.println(createSQL("eai.", tableName, columns));
    }
}
