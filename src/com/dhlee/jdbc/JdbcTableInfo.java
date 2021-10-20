package com.dhlee.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;	
import java.util.List;

public class JdbcTableInfo {
	public static void main(String args[]) {
		try {
			String tableName = "APITABLELOG"; //"LOBTEST";
			System.out.println("<< MariaDB >>");
			System.out.println(">> getTableInfo - "+ tableName);
			List cols = getTableInfo("MariaDB", "EAI", tableName);
			printColumnList(cols);
			System.out.println(">> getTableInfoWithRs - "+ tableName);
			cols = getTableInfoWithRs("MariaDB", "EAI", tableName);
			printColumnList(cols);
			System.out.println("<< Oracle >>");
			System.out.println(">> getTableInfo - "+ tableName);
			cols = getTableInfo("Oracle", "EAI", tableName);
			printColumnList(cols);
			System.out.println(">> getTableInfoWithRs - "+ tableName);
			cols = getTableInfoWithRs("Oracle", "EAI", tableName);
			printColumnList(cols);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void printColumnList(List<ColumnInfo> list) {
		for(ColumnInfo col : list) {
			System.out.println(col.toString());
		}
	}
	
	public static List<ColumnInfo> getTableInfo(String dbType, String owner, String tableName) {		
		Connection con = null;
		ResultSet columns = null;
		ResultSet pks = null;
		String url = null;
		String user = null;
		String userpass = null;
		
		List<ColumnInfo> tableInfoMap = new LinkedList<ColumnInfo>();
		try {
			if(dbType != null) dbType = dbType.toUpperCase();
			if("ORACLE".equals(dbType)) {
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				url = "jdbc:oracle:thin:@localhost:1521:XE";
				user = "eai";
				userpass = "eaiadmin";
			}
			else if("MARIADB".equals(dbType)) {
				DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
				url = "jdbc:mariadb://localhost:3306/eai?characterEncoding=euckr";
				user = "eai";
				userpass = "eaiadmin";
			}
			else {
				System.out.println("unsupported dbType = " + dbType);
				return null;
			}
			con = DriverManager.getConnection(url, user, userpass);
			DatabaseMetaData metaData = con.getMetaData();
			String databaseProductName = metaData.getDatabaseProductName();
			System.out.println("databaseProductName: " + databaseProductName);
			
			pks = metaData.getPrimaryKeys(null, owner, tableName);
			HashMap<String,ResultSet> keyMap = new HashMap<String,ResultSet>();
			int pos = 0;
			while (pks.next()) {
				System.out.println("PrimaryKey Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
				keyMap.put(pks.getString("COLUMN_NAME"), pks);
				pos++;
			}
			
			if(keyMap.size() == 0) {
				pos = 0;
				pks = metaData.getIndexInfo(null, owner, tableName, true, true);
				while (pks.next()) {
					System.out.println("Unique Index Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
					keyMap.put(pks.getString("COLUMN_NAME"), pks);
					pos++;
				}
			}
			columns = metaData.getColumns(null, owner, tableName, null);
			while (columns.next()) {
				ColumnInfo col = new ColumnInfo();
				col.setColumnName(columns.getString("COLUMN_NAME"));
				col.setDataTypeName(columns.getString("TYPE_NAME"));
				col.setColumnSize(columns.getInt("COLUMN_SIZE"));
				if("1".equals(columns.getString("NULLABLE"))) {
					col.setNullable(true);
				}
				else {
					col.setNullable(false);
				}
				if(keyMap.get(col.getColumnName()) == null) {
					col.setPrimaryKey(false);
				}
				else {
					col.setPrimaryKey(true);
				}
				System.out.println(col.toString());
			}			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(columns != null) {
				try { columns.close(); } catch(SQLException ex) {}
			}
			if(pks != null) {
				try { pks.close(); } catch(SQLException ex) {}
			}
			if(con != null) {
				try { con.close(); } catch(SQLException ex) {}
			}
		}
		return tableInfoMap;
	}
	
	public static List<ColumnInfo> getTableInfoWithRs(String dbType, String owner, String tableName) {		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet pks = null;
		String url = null;
		String user = null;
		String userpass = null;
		
		List<ColumnInfo> tableInfoMap = new LinkedList<ColumnInfo>();
		try {
			if(dbType != null) dbType = dbType.toUpperCase();
			if("ORACLE".equals(dbType)) {
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
				url = "jdbc:oracle:thin:@localhost:1521:XE";
				user = "eai";
				userpass = "eaiadmin";
			}
			else if("MARIADB".equals(dbType)) {
				DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
				url = "jdbc:mariadb://localhost:3306/eai?characterEncoding=euckr";
				user = "eai";
				userpass = "eaiadmin";
			}
			else {
				System.out.println("unsupported dbType = " + dbType);
				return null;
			}
			con = DriverManager.getConnection(url, user, userpass);
			DatabaseMetaData metaData = con.getMetaData();
			String databaseProductName = metaData.getDatabaseProductName();
			System.out.println("databaseProductName: " + databaseProductName);
			
			pks = metaData.getPrimaryKeys(null, owner, tableName);
			HashMap<String,ResultSet> keyMap = new HashMap<String,ResultSet>();
			int pos = 0;
			while (pks.next()) {
				System.out.println("PrimaryKey Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
				keyMap.put(pks.getString("COLUMN_NAME"), pks);
				pos++;
			}
			
			if(keyMap.size() == 0) {
				pos = 0;
				pks = metaData.getIndexInfo(null, owner, tableName, true, true);
				while (pks.next()) {
					System.out.println("Unique Index Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
					keyMap.put(pks.getString("COLUMN_NAME"), pks);
					pos++;
				}
			}
			
			String sql = "select * from " +owner + "." + tableName + " where 1=2";
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			ResultSetMetaData md = rs.getMetaData();
			for (int i=1; i<=md.getColumnCount(); i++) {
				ColumnInfo col = new ColumnInfo();
				col.setColumnName(md.getColumnLabel(i));
				col.setDataTypeName(md.getColumnTypeName(i));
				col.setColumnSize(md.getScale(i));
				if( 1 == md.isNullable(i) ) {
					col.setNullable(true);
				}
				else {
					col.setNullable(false);
				}
				if(keyMap.get(col.getColumnName()) == null) {
					col.setPrimaryKey(false);
				}
				else {
					col.setPrimaryKey(true);
				}
				System.out.println(col.toString());
			}
					
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null) {
				try { rs.close(); } catch(SQLException ex) {}
			}
			if(pks != null) {
				try { pks.close(); } catch(SQLException ex) {}
			}
			if(st != null) {
				try { st.close(); } catch(SQLException ex) {}
			}
			if(con != null) {
				try { con.close(); } catch(SQLException ex) {}
			}
		}
		return tableInfoMap;
	}
}
