package com.dhlee;

import java.net.URI;
import java.net.URLEncoder;

public class UriTest {

	public static void main(String[] args) {
		String uriString = "https://example.com/auth/memregchk/?PARAM=";
		String paramData = "{\"ticket\":\"abcd1234\"}";
		
		try {
			URI uri = URI.create(uriString + paramData);
			System.out.println(uri);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			paramData = URLEncoder.encode(paramData, "utf-8");
			URI uri = URI.create(uriString + paramData);
			System.out.println(uri);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
