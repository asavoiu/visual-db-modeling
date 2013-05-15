package ro.visualDB.logging;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

public class AppLogger {
	public static Logger logger = null;

	static {
		// Configure logger from configuration file
		try {
			URL url = AppLogger.class.getResource("log4j.properties");
			if (url != null)
				PropertyConfigurator.configure(url);
		} catch (Exception e) {
		} finally {
			// Configure with default settings
			try {
				Layout layout = new PatternLayout("(%d{yyyy-MM-dd hh:mm:ss}) %p %C [%M:%L] - %m%n");
				Appender appender = new FileAppender(layout, "./log/visualDB.log", true);
				BasicConfigurator.configure(appender);
				Logger.getRootLogger().setLevel(Level.ALL);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static Logger getLogger() {
		if (logger == null) {
			logger = Logger.getRootLogger();
			return logger;
		} else {
			return logger;
		}
	}
	
	public static void setLevel(Level level) {
		if (logger != null) {
			logger.setLevel(level);
		}
	}
	
}
