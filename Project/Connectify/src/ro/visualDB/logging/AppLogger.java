package ro.visualDB.logging;

import org.apache.log4j.Logger;

public class AppLogger {
	public static Logger logger = null;

	
	public static Logger getLogger() {
		if (logger == null) {
			logger = Logger.getRootLogger();
			return logger;
		} else {
			return logger;
		}
		
	}
	
}
