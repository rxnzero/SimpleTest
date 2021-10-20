package com.dhlee.jdbc;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcMetaDataTest {
	public static void main(String args[]) {
		try {
			String tableName = "APITABLELOG"; //"LOBTEST";
			System.out.println(">> testMetaData MariaDB - "+ tableName);
			testMetaData("MariaDB", "EAI", tableName);
			System.out.println(">> testMetaData Oracle - "+ tableName);
			testMetaData("Oracle", "EAI", tableName);
			
			String sql = null; 
			
//			sql = "SELECT * FROM EAI.TEST limit 1";
//			sql = "SELECT * FROM EAI.COMPANY where 1 = 2";
//			sql = "SELECT * FROM EAI.LOBTEST";
			sql = "SELECT * FROM EAI.APITABLELOG  where 1 = 2";
			System.out.println(">> testRsMetaData MariaDB - "+ sql);
			testRsMetaData("MariaDB", sql);
			
//			sql = "SELECT * FROM EAI.LOBTEST where rownum = 1";
			System.out.println(">> testRsMetaData Oracle - "+ sql);
			testRsMetaData("Oracle", sql);
//			testInsertOracleBlob();
			
//			String sql =  "SELECT * FROM apisvcparams ";
//			System.out.println(sql +"\n=> " +getOracleLimitQuery(sql));
//			
//			sql =  "SELECT * FROM apisvcparams where SERVICEID like 'ODA%'";
//			System.out.println(sql +"\n=> " +getOracleLimitQuery(sql));
//			
//			sql =  "SELECT * FROM apisvcparams where SERVICEID like 'ODA%'\norder by 1";
//			System.out.println(sql +"\n=> " +getOracleLimitQuery(sql));
//			
//			sql =  "SELECT * FROM apisvcparams order by 1";
//			System.out.println(sql +"\n=> " +getOracleLimitQuery(sql));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static String getOracleLimitQuery(String sql) {
		String addsql = " rownum <= 100 ";
		
		sql = sql.replaceAll("\n", " ");
		String upperSQL = sql.toUpperCase();
		
		int wherePos = upperSQL.indexOf(" WHERE ");
		int orderPos = upperSQL.indexOf(" ORDER ");
		
		if(wherePos < 0 && orderPos < 0) {
			addsql = sql + " WHERE" + addsql;
		}
		else if(wherePos > 0 && orderPos > 0) {
			addsql = sql.substring(0, orderPos) + " AND" + addsql + sql.substring(orderPos);
		}
		else if(wherePos > 0 && orderPos < 0) {
			addsql = sql + " AND " + addsql;
		}
		else if(wherePos < 0 && orderPos > 0) {
			addsql = sql.substring(0, orderPos) + " WHERE" + addsql + sql.substring(orderPos);
		}
		return addsql;
	}
	
	public static void testMetaData(String dbType, String owner, String tableName) {		
		Connection con = null;
		ResultSet columns = null;
		ResultSet pks = null;
		String url = null;
		String user = null;
		String userpass = null;
		
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
				return;
			}
			con = DriverManager.getConnection(url, user, userpass);
			System.out.println("Connection established......");
			DatabaseMetaData metaData = con.getMetaData();
			String databaseProductName = metaData.getDatabaseProductName();
			System.out.println("databaseProductName: " + databaseProductName);
			columns = metaData.getColumns(null, owner, tableName, null);
			while (columns.next()) {
				System.out.print("COLUMN_NAME: " + columns.getString("COLUMN_NAME"));
				System.out.print("COLUMN_SIZE (" + columns.getInt("COLUMN_SIZE") + ")");
				System.out.println(" ");
//				System.out.println("Ordinal position: " + columns.getInt("ORDINAL_POSITION"));
//				System.out.println("Catalog: " + columns.getString("TABLE_CAT"));
//				System.out.println("DATA_TYPE (integer value): " + columns.getInt("DATA_TYPE"));
				System.out.println("TABLE_NAME: " + columns.getString("TABLE_NAME"));
				System.out.println("TYPE_NAME: " + columns.getString("TYPE_NAME"));
				System.out.println("NULLABLE: " + columns.getString("NULLABLE"));
				System.out.println("REMARKS: " + columns.getString("REMARKS"));
				System.out.println(" ");
			}
			System.out.println(">> owner="+owner);
			System.out.println(">> tableName="+tableName);
			pks = metaData.getPrimaryKeys(null, owner, tableName);
			int pos = 1;
			while (pks.next()) {
				System.out.print("PrimaryKey Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
				System.out.println(" ");
				pos++;
			}
			
			pos = 1;
			pks = metaData.getIndexInfo(null, owner, tableName, true, true);
			while (pks.next()) {
				System.out.print("Unique Index Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
				System.out.println(" ");
				pos++;
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
	}
	
	public static void testRsMetaData(String dbType, String sql) {		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		ResultSet pks = null;
		String url = null;
		String user = null;
		String userpass = null;
		
		String owner = null;
		String tableName = null;
		
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
				return;
			}
			con = DriverManager.getConnection(url, user, userpass);
			System.out.println("Connection established......");
			DatabaseMetaData metaData = con.getMetaData();
			String databaseProductName = metaData.getDatabaseProductName();
			System.out.println("databaseProductName: " + databaseProductName);
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			ResultSetMetaData md = rs.getMetaData();
			StringBuffer sb = new StringBuffer();
			for (int i=1; i<=md.getColumnCount(); i++)
			{
				sb.append("ColumnLabel=").append(md.getColumnLabel(i));
				sb.append(", ColumnType=").append(md.getColumnType(i));
				sb.append(", ColumnTypeName=").append(md.getColumnTypeName(i));
				sb.append(", isNullable=").append(md.isNullable(i));
				System.out.println(sb.toString());
				sb.setLength(0);
				if(i==1) {
					owner = md.getSchemaName(i);
					System.out.println(">> getSchemaName="+owner);
					owner = md.getCatalogName(i);
					System.out.println(">> getCatalogName="+owner);
					tableName = md.getTableName(i);
					System.out.println(">> owner="+owner);
					System.out.println(">> tableName="+tableName);
					
				}
			}	
			
//			System.out.println(">> owner="+owner);
//			System.out.println(">> tableName="+tableName);
//			owner = "EAI";
//			tableName = "APITABLELOG";
	
			DatabaseMetaData dbMetaData = con.getMetaData();
			pks = dbMetaData.getPrimaryKeys(null, owner, tableName);
			int pos = 1;
			while (pks.next()) {
				System.out.print("PrimaryKey Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
				System.out.println(" ");
				pos++;
			}
			
			pos = 1;
			pks = dbMetaData.getIndexInfo(null, owner, tableName, true, true);
			while (pks.next()) {
				System.out.print("Unique Index Column["+pos+"] name : " + pks.getString("COLUMN_NAME"));
				System.out.println(" ");
				pos++;
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null) {
				try { rs.close(); } catch(SQLException ex) {}
			}
			if(st != null) {
				try { st.close(); } catch(SQLException ex) {}
			}
			if(con != null) {
				try { con.close(); } catch(SQLException ex) {}
			}
		}
	}
	
	public static void testInsertOracleBlob() throws SQLException {
		// Registering the Driver
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		// Getting the connection
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		Connection con = DriverManager.getConnection(url, "eai", "eaiadmin");
		System.out.println("Connection established......");
		String sql = "insert into EAI.LOBTEST(keyname, keyvalue, bindata) values ( ?, ?, ?)";
		PreparedStatement ps = con.prepareStatement(sql);
		byte[] content = "12345".getBytes();		
		ps.setString(1, "test");
		ps.setString(2, "blob ют╥б");
		ps.setBlob(3, new ByteArrayInputStream(content), content.length);
		int count = ps.executeUpdate();
		
	}
}
