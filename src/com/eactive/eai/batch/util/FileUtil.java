package com.eactive.eai.batch.util;

import java.io.File;

public class FileUtil {

	public static boolean createFilePath(String filePath) {
	      File file = new File(filePath);
	      if(!file.isDirectory()) {
	    	  file = file.getParentFile();
	      }
	      boolean result = file.mkdirs();	      
	      return result;
	}
	
	public static void main(String[] args) {
		System.out.println("createFilePath = " + createFilePath("D:/new/test/test.txt"));
	}

}
