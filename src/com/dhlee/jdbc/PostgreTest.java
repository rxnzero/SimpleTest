package com.dhlee.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreTest {

	public PostgreTest() {
		// TODO Auto-generated constructor stub
	}

	  public static void main(String[] args) throws Exception {
	    
	    String url = "jdbc:postgresql:postgres";  
	    String usr = "eai";  
	    String pwd = "postgre";

	     Class.forName("org.postgresql.Driver");
	    
	    // -- 1
	    Connection conn = DriverManager.getConnection(url, usr, pwd);
	    System.out.println(conn);
	    conn.close();
	    
	    // -- 2
	    url = "jdbc:postgresql://localhost/postgres";
	    conn = DriverManager.getConnection(url, usr, pwd);
	    System.out.println(conn);
	    conn.close();
	    
	    // -- 3
	    url = "jdbc:postgresql://localhost:5432/postgres";
	    conn = DriverManager.getConnection(url, usr, pwd);
	    System.out.println(conn);
	    conn.close();
	  }
	}
