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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Props {

	private final static Logger LOGGER = Logger.getLogger(Props.class.getName());
	private static final String CONFIG_PROPERTIES = "config.properties";
	private static final String GLOBAL_PROPERTIES = "global.properties";
	private static Properties prop = new Properties();

	static {
		try {

			//if (!new File(CONFIG_PROPERTIES).exists()) {
				// Assume it doesnt exist so write defaults
			//	Props.setProperty(GlobalProperties.SORT_BY_NAME, GlobalProperties.SORT_BY_NAME_DEFAULT_VALUE, false);
			//}

			new FileOutputStream(Props.CONFIG_PROPERTIES, true).close();
			new FileOutputStream(Props.GLOBAL_PROPERTIES, true).close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setProperty(String propKey, String propVal, boolean userProps) {
		File f = new File(userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES);
		if (!f.exists() && !f.isDirectory()) {
			Path file = Paths.get(userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES);
			try {
				Files.write(file, new ArrayList<String>(), Charset.forName("UTF-8"));
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "", e);
			}
		}
		try (InputStream input = new FileInputStream(userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES);) {
			prop.load(input);
			// set the properties value
			prop.put(propKey, propVal);
			// save properties to project root folder
			try (OutputStream output = new FileOutputStream(userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES);) {
				prop.store(output, null);
			}

		} catch (IOException io) {
			LOGGER.log(Level.SEVERE, "", io);
			throw new RuntimeException("value not set for key: " + propKey + " - value: " + propVal);
		}
	}

	public static String getProperty(String propKey, boolean userProps) {
		try (InputStream input = new FileInputStream(userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES);) {
			// load a properties file
			prop.load(input);
			return prop.getProperty(propKey);
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, "", ex);
			throw new RuntimeException("value not found for key: " + propKey);
		}

	}

	public static void deleteProperty(String string, boolean userProps) {
		try (InputStream input = new FileInputStream(userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES);) {
			prop.load(input);
			// set the properties value
			prop.remove(string);
			// save properties to project root folder
			try (OutputStream output = new FileOutputStream(userProps ? CONFIG_PROPERTIES : GLOBAL_PROPERTIES);) {
				prop.store(output, null);
			}

		} catch (IOException io) {
			LOGGER.log(Level.SEVERE, "", io);
			throw new RuntimeException("value not deleted for key: " + string);
		}
	}

	public static class GlobalProperties {
		public static String SORT_BY_NAME = "soryByName";
		public static String SORT_BY_NAME_DEFAULT_VALUE = "true";
	}
}