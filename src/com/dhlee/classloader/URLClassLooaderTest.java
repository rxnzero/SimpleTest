package com.dhlee.classloader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class URLClassLooaderTest {

	public URLClassLooaderTest() {
		
	}
	
	public static void main(String[] argv) throws Exception {
		try{
			
			File file = new File("d:\\proxy\\lib\\elink-proxy.jar"); 
			URL url = file.toURI().toURL(); 
			URL[] urls = new URL[]{url}; 
				
			ClassLoader cl = new URLClassLoader(urls); 

			Class  cls = cl.loadClass("com.eactive.eai.util.ZipTestClient");		

			ProtectionDomain pDomain = cls.getProtectionDomain(); 
			CodeSource cSource = pDomain.getCodeSource(); 
			URL urlfrom = cSource.getLocation();
			System.out.println("Class Loading Location : " + urlfrom.getFile());
		
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		
	}
}
