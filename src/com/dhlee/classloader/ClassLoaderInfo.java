package com.dhlee.classloader;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ClassLoaderInfo {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	public Map<String, Object> getLoadingClassInfo(String className) {
		Map<String, Object> result = new HashMap<String, Object>();
		List cl = null;
		try {
			cl = getClassLoadersOfClass(className);
		} catch (Throwable e) {
			result.put("X_ERROR", e);
			return result;
		}
		if (cl != null)
			result.put("X_CLASSLOADERS", cl);
		String resourceName = classNameToResourceName(className);
		URL url = getClass().getResource(resourceName);
		try {
			result.put("X_FILE_NAME", url.toURI().toString());
			File file = getFileFromURI(url.toURI());
			if (file.exists()) {
				result.put("X_FILE_DATE", sdf.format(new Date(file.lastModified())));
			}
		} catch (URISyntaxException e) {
		}
		return result;
	}

	public File getFileFromURI(URI uri) {
		File result = null;
		String schema = uri.getScheme();
		if ("file".equals(schema))
			result = new File(uri);
		else if ("zip".equals(schema)) {
			String uriStr = uri.getSchemeSpecificPart();
			String file = uriStr.substring(0, uriStr.indexOf('!'));
			result = new File(file);
		} else if ("jar".equals(schema)) {
			String uriStr = uri.getSchemeSpecificPart();
			String file = uriStr.substring(6, uriStr.indexOf('!'));
			result = new File(file);
		}
		return result;
	}

	public List getClassLoaderInfos(String className) {
		List result = new ArrayList();
		for (ClassLoader cl = getClass().getClassLoader(); cl != null; cl = cl.getParent())
			result.add(cl.getClass().getName());

		result.add("Bootstrap classloader");
		return result;
	}

	public List getBootClassInfos() {
		return separatePath(System.getProperty("sun.boot.class.path"));
	}

	public List getExtClassInfos() {
		return getJars(System.getProperty("java.ext.dirs"));
	}

	public List getAppClassInfos() {
		return separatePath(System.getProperty("java.class.path"));
	}

	private List separatePath(String classpath) {
		StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);
		List result = new ArrayList();
		for (; st.hasMoreTokens(); result.add(st.nextToken()))
			;
		return result;
	}

	private List getClassLoadersOfClass(String className) throws Throwable {
		Class c = Class.forName(className.trim());
		List result = new ArrayList();
		for (ClassLoader cl = c.getClassLoader(); cl != null; cl = cl.getParent())
			result.add(cl.getClass().getName());

		result.add("Bootstrap classloader");
		return result;
	}

	private String classNameToResourceName(String className) {
		String resourceName = className;
		if (!resourceName.startsWith("/"))
			resourceName = (new StringBuilder()).append("/").append(resourceName).toString();
		resourceName = resourceName.replace('.', '/');
		resourceName = (new StringBuilder()).append(resourceName).append(".class").toString();
		return resourceName;
	}

	private List getJars(String directoryName) {
		File directory = new File(directoryName);
		if (!directory.exists())
			return new ArrayList();
		List result = new ArrayList();
		String allFiles[] = directory.list();
		if (allFiles != null) {
			for (int i = 0; i < allFiles.length; i++)
				if (allFiles[i].endsWith(".jar")) {
					File f = new File(directory, allFiles[i]);
					result.add(f.getPath());
				}

		}
		return result;
	}

	public static void main(String[] argv) {
		ClassLoaderInfo cl = new ClassLoaderInfo();
		String className = "oracle.jdbc.driver.OracleDriver";

		Map<String, Object>  classInfo = null;
		if (className != null && className.trim().length() > 0) {
			classInfo = cl.getLoadingClassInfo(className);
		}

		List bootClasses = cl.getBootClassInfos();
		List extClasses = cl.getExtClassInfos();
		List appClasses = cl.getAppClassInfos();

		if (classInfo != null && classInfo.get("X_ERROR") != null) {
			Throwable t = (Throwable) classInfo.get("X_ERROR");
			System.out.println(String.format("No such class : %s (%s)", t.getClass().getName(), t.getMessage()));
		} else if (classInfo != null) {
			System.out.println("Class Name = " + className); 
			System.out.println("FIle Name = " + classInfo.get("X_FILE_NAME"));
			System.out.println("FIle Date = " + classInfo.get("X_FILE_DATE"));
			
			System.out.println("\n#X_CLASSLOADERS");
			List classLoaders = (List) classInfo.get("X_CLASSLOADERS");
			for (int i = 0; i < classLoaders.size(); i++) {
				String classLoader = (String) classLoaders.get(i);
				System.out.println("classLoader = " + classLoader);
			}

			System.out.println("\n#BOOT_CLASSLOADERS");
			for (int i = 0; i < bootClasses.size(); i++) {
				String path = (String) bootClasses.get(i);
				System.out.println("bootClasses = " + path);
			}

			System.out.println("\n#EXT_CLASSLOADERS");
			for (int i = 0; i < extClasses.size(); i++) {
				String path = (String) extClasses.get(i);
				System.out.println("extClasses = " + path);
			}

			System.out.println("\n#APP_CLASSLOADERS");
			for (int i = 0; i < appClasses.size(); i++) {
				String path = (String) appClasses.get(i);
				System.out.println("appClasses = " + path);
			}
		}
	}

}
