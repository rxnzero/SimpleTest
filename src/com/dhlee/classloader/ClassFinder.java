package com.dhlee.classloader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import sun.net.www.protocol.file.FileURLConnection;

public class ClassFinder {

	public ClassFinder() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static Class[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class> classes = new ArrayList<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class> classes = new ArrayList<Class>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
	
	/**
	 * Private helper method
	 * 
	 * @param directory
	 *            The directory to start with
	 * @param pckgname
	 *            The package name to search for. Will be needed for getting the
	 *            Class object.
	 * @param classes
	 *            if a file isn't loaded but still is in the directory
	 * @throws ClassNotFoundException
	 */
	private static void checkDirectory(File directory, String pckgname,
	        ArrayList<Class<?>> classes) throws ClassNotFoundException {
	    File tmpDirectory;

	    if (directory.exists() && directory.isDirectory()) {
	        final String[] files = directory.list();

	        for (final String file : files) {
	            if (file.endsWith(".class")) {
	                try {
	                    classes.add(Class.forName(pckgname + '.'
	                            + file.substring(0, file.length() - 6)));
	                } catch (final NoClassDefFoundError e) {
	                    // do nothing. this class hasn't been found by the
	                    // loader, and we don't care.
	                }
	            } else if ((tmpDirectory = new File(directory, file))
	                    .isDirectory()) {
	                checkDirectory(tmpDirectory, pckgname + "." + file, classes);
	            }
	        }
	    }
	}

	/**
	 * Private helper method.
	 * 
	 * @param connection
	 *            the connection to the jar
	 * @param pckgname
	 *            the package name to search for
	 * @param classes
	 *            the current ArrayList of all classes. This method will simply
	 *            add new classes.
	 * @throws ClassNotFoundException
	 *             if a file isn't loaded but still is in the jar file
	 * @throws IOException
	 *             if it can't correctly read from the jar file.
	 */
	private static void checkJarFile(JarURLConnection connection,
	        String pckgname, ArrayList<Class<?>> classes)
	        throws ClassNotFoundException, IOException {
	    final JarFile jarFile = connection.getJarFile();
	    final Enumeration<JarEntry> entries = jarFile.entries();
	    String name;

	    for (JarEntry jarEntry = null; entries.hasMoreElements()
	            && ((jarEntry = entries.nextElement()) != null);) {
	        name = jarEntry.getName();

	        if (name.contains(".class")) {
	            name = name.substring(0, name.length() - 6).replace('/', '.');

	            if (name.contains(pckgname)) {
	            	try {
	            		Class cl= Class.forName(name);
	            		if(cl != null) classes.add(cl);
	            	}
	            	catch(Throwable t) {
	            		//System.out.println(t.getMessage());
	            	}
	            }
	        }
	    }
	}

	/**
	 * Attempts to list all the classes in the specified package as determined
	 * by the context class loader
	 * 
	 * @param pckgname
	 *            the package name to search
	 * @return a list of classes that exist within that package
	 * @throws ClassNotFoundException
	 *             if something went wrong
	 */
	public static ArrayList<Class<?>> getClassesForPackage(String pckgname)
	        throws ClassNotFoundException {
	    final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

	    try {
	        final ClassLoader cld = Thread.currentThread()
	                .getContextClassLoader();

	        if (cld == null)
	            throw new ClassNotFoundException("Can't get class loader.");

	        final Enumeration<URL> resources = cld.getResources(pckgname
	                .replace('.', '/'));
	        URLConnection connection;

	        for (URL url = null; resources.hasMoreElements()
	                && ((url = resources.nextElement()) != null);) {
	            try {
	                connection = url.openConnection();

	                if (connection instanceof JarURLConnection) {
	                    checkJarFile((JarURLConnection) connection, pckgname,
	                            classes);
	                } else if (connection instanceof FileURLConnection) {
	                    try {
	                        checkDirectory(
	                                new File(URLDecoder.decode(url.getPath(),
	                                        "UTF-8")), pckgname, classes);
	                    } catch (final UnsupportedEncodingException ex) {
	                        throw new ClassNotFoundException(
	                                pckgname
	                                        + " does not appear to be a valid package (Unsupported encoding)",
	                                ex);
	                    }
	                } else
	                    throw new ClassNotFoundException(pckgname + " ("
	                            + url.getPath()
	                            + ") does not appear to be a valid package");
	            } catch (final IOException ioex) {
	                throw new ClassNotFoundException(
	                        "IOException was thrown when trying to get all resources for "
	                                + pckgname, ioex);
	            }
	        }
	    } catch (final NullPointerException ex) {
	        throw new ClassNotFoundException(
	                pckgname
	                        + " does not appear to be a valid package (Null pointer exception)",
	                ex);
	    } catch (final IOException ioex) {
	        throw new ClassNotFoundException(
	                "IOException was thrown when trying to get all resources for "
	                        + pckgname, ioex);
	    }

	    return classes;
	}
	public static void main(String[] args) {
		String packageName  = null;
//		packageName = "com.dhlee"; 
		packageName = "oracle.jdbc.driver";
		
		System.out.println(String.format(">> Package name : %s",packageName) );
		try {
			Class[] classes = ClassFinder.getClasses(packageName);
			if(classes.length > 0) {
				for(int i=0; i<classes.length; i++) {
					Class cl = classes[i];
					System.out.println( String.format("Class [%d] %s, %s", i, cl.getName(), cl.getCanonicalName() ) );
				}
			}
			else {
				System.out.println(String.format("Package not found : %s",packageName) );
			}
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		System.out.println(String.format("\n>> getClassesForPackage -> Package name : %s",packageName) );
		try {
			List<Class<?>> classList = ClassFinder.getClassesForPackage(packageName);
			int i=0;
			for(Class cl : classList) {
				System.out.println(String.format("Class [%d] %s, %s", i, cl.getName(), cl.getCanonicalName() ) );
				i++;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
