package ro.visualDB.xml;

import java.io.File;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;

import ro.visualDB.logging.AppLogger;

public class XMLElementFactory {
	private static HashMap<String, Class<? extends Object>> classList;
	private static String packageName = "ro";
	
	private static HashMap<String, Class<? extends Object>> getAllClasses(File file)
			throws Exception {
		HashMap<String, Class<? extends Object>> res;
		res = new HashMap<String, Class<? extends Object>>();
		int start;
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					res.putAll(getAllClasses(f));
				} else if (f.getAbsolutePath().endsWith(".class")) {
					String fileName = f.getAbsolutePath();
					start = fileName.lastIndexOf(File.separator +
							packageName.replace('.', File.separatorChar) +
							File.separator);
					String className = fileName.substring(start + 1, fileName.length() - 6)
							.replace(File.separatorChar, '.');
					Class<? extends Object> cls =  Class.forName(className);
					if (!cls.isInterface()) {
						if (cls.getSuperclass().equals(TreeNode.class)) {
							res.put(f.getName()
									.substring(0, f.getName().length() - 6)
									.toLowerCase(), cls);
						} else {
							Type[] types = cls.getInterfaces();
							for (Type t : types) {
								if (t.equals(XMLElement.class)) {
									res.put(f.getName()
											.substring(0, f.getName().length() - 6)
											.toLowerCase(), cls);
									break;
								}
							}
						}
					}
				}
			}
		} else {
			if (file.getAbsolutePath().endsWith(".class")) {
				String fileName = file.getAbsolutePath();
				start = fileName.lastIndexOf(File.separator
						+ packageName.replace('.', File.separatorChar) 
						+ File.separator);
				String className = fileName.substring(start + 1, fileName.length() - 6)
						.replace(File.separatorChar, '.');
				Class<? extends Object> cls =  Class.forName(className);
				if (!cls.isInterface()) {
					if (cls.getSuperclass().equals(TreeNode.class)) {
						res.put(file.getName()
								.substring(0, file.getName().length() - 6)
								.toLowerCase(), cls);
					} else {
						Type[] types = cls.getInterfaces();
						for (Type t : types) {
							if (t.equals(XMLElement.class)) {
								res.put(file.getName()
										.substring(0, file.getName().length() - 6)
										.toLowerCase(), cls);
								break;
							}
						}
					}
				}
			}
		}
		return res;
	}
	
	static {
		try {
			classList = new HashMap<String, Class<? extends Object>>();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			// Resources are separated by '/' even if the 
			// file separator may be '\\'
			Enumeration<URL> resources ;
			resources = classLoader.getResources(packageName.replace('.', '/'));
			
			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();
				File file = new File(url.getFile());
				classList.putAll(getAllClasses(file));
			}
		} catch (Exception e) {
			AppLogger.getLogger().error("Error loading classes", e);
		}
	}
	
	
	/**
	 * Returns the class which implements XMLElement
	 * from the sources and has
	 * Name.toLowercase() = name
	 * @param name class name in Lower Case
	 * @return XMLElement object
	 */
	public static XMLElement newXMLElementInstance(String name) {
		try {
			return (XMLElement)classList.get(name).newInstance();
		} catch (Exception e) {
			AppLogger.getLogger().error("Instantiation error", e);
			return null;
		}
	}
}
