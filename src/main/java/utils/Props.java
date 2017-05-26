package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Props {

	private final static Logger log = LogManager.getLogger(Props.class);
	private static final String CONFIG_PROPERTIES = "config.properties";
	private static final String GLOBAL_PROPERTIES = "global.properties";

	static {
		try {

			boolean exists = new File(GLOBAL_PROPERTIES).exists();

			new FileOutputStream(Props.CONFIG_PROPERTIES, true).close();
			new FileOutputStream(Props.GLOBAL_PROPERTIES, true).close();

			if (!exists) {
				Props.setGlobalProperty(GlobalProperties.SORT_BY_NAME, GlobalProperties.SORT_BY_NAME_DEFAULT_VALUE);
				Props.setGlobalProperty(GlobalProperties.APP_NAME, GlobalProperties.APP_NAME_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.SHOW_COUNTER_OPTIONS, GlobalProperties.SHOW_COUNTER_OPTIONS_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.SHOW_COUTER_POPUP_EACH_START, GlobalProperties.SHOW_COUTER_POPUP_EACH_START_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.VERSION, GlobalProperties.VERSION_DEFAULT);
			}

		} catch (IOException e) {
			log.error(e);
		}

	}

	private static void setGlobalProperty(String propKey, String propVal) {
		setProperty(propKey, propVal, false);
	}

	public static void setUserProperty(String propKey, String propVal) {
		setProperty(propKey, propVal, true);
	}

	public static String getGlobalProperty(String propKey) {
		return getProperty(propKey, false);
	}

	public static String getLangProperty(String propKey) {
		String propFile = "messages.properties";
		return getPropertyInternal(propKey, propFile);
	}

	public static String getUserProperty(String propKey) {
		return getProperty(propKey, true);
	}

	private static String getProperty(String propKey, boolean userProps) {
		String propFile = userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES;
		return getPropertyInternal(propKey, propFile);
	}

	private static void setProperty(String propKey, String propVal, boolean userProps) {

		String propFile = userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES;
		File f = new File(propFile);
		if (!f.exists() && !f.isDirectory()) {
			Path file = Paths.get(propFile);
			try {
				Files.write(file, new ArrayList<String>(), Charset.forName("UTF-8"));
			} catch (IOException e) {
				log.error(e);
			}
		}
		try (InputStream input = new FileInputStream(propFile);) {
			Properties prop = new Properties();
			prop.load(input);
			// set the properties value
			prop.put(propKey, propVal);
			// save properties to project root folder
			try (OutputStream output = new FileOutputStream(propFile);) {
				prop.store(output, null);
			}

		} catch (IOException io) {
			log.error(io);
			throw new RuntimeException("value not set for key: " + propKey + " - value: " + propVal);
		}
	}

	private static String getPropertyInternal(String propKey, String propFile) {
		try (InputStream input = new FileInputStream(propFile);) {
			Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			return prop.getProperty(propKey);
		} catch (IOException ex) {
			log.error(ex);
			throw new RuntimeException("value not found for key: " + propKey);
		}
	}

	public static void deleteProperty(String string, boolean userProps) {
		String propFile = userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES;
		try (InputStream input = new FileInputStream(propFile);) {
			Properties prop = new Properties();
			prop.load(input);
			// set the properties value
			prop.remove(string);
			// save properties to project root folder
			try (OutputStream output = new FileOutputStream(propFile);) {
				prop.store(output, null);
			}

		} catch (IOException io) {
			log.error(io);
			throw new RuntimeException("value not deleted for key: " + string);
		}
	}

	public static class GlobalProperties {
		public static String SORT_BY_NAME = "soryByName";
		public static String SORT_BY_NAME_DEFAULT_VALUE = "true";

		public static String APP_NAME = "appName";
		public static String APP_NAME_DEFAULT = "Build";

		public static String SHOW_COUNTER_OPTIONS = "showCounterOptions";
		public static String SHOW_COUNTER_OPTIONS_DEFAULT = "true";

		public static String SHOW_COUTER_POPUP_EACH_START = "showCounterPopUPOnEachStart";
		public static String SHOW_COUTER_POPUP_EACH_START_DEFAULT = "false";
		
		public static String VERSION = "Version";
		public static String VERSION_DEFAULT = "1.0.0.2";
	}
}