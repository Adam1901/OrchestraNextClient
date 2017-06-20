package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

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

			// Out right write the file if it doesnt exist.
			if (!exists) {
				Props.setGlobalProperty(GlobalProperties.SORT_BY_NAME, GlobalDefaults.SORT_BY_NAME_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.APP_NAME, GlobalDefaults.APP_NAME_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.SHOW_COUNTER_OPTIONS,
						GlobalDefaults.SHOW_COUNTER_OPTIONS_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.SHOW_COUTER_POPUP_EACH_START,
						GlobalDefaults.SHOW_COUTER_POPUP_EACH_START_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.VERSION, GlobalDefaults.VERSION_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.CALL_FORWARDS, GlobalDefaults.CALL_FORWARDS_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.CALL_FORWARDS_SERVICE,
						GlobalDefaults.CALL_FORWARDS_SERVICE_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.NOTIFICATIONS, GlobalDefaults.NOTIFICATIONS_DEFAULT);
				Props.setGlobalProperty(GlobalProperties.RECEPTION1WORKSTATION2BOTH0,
						GlobalDefaults.RECEPTION1WORKSTATION2BOTH0_DEFAULT);
			}

			Map<String, String> valueMap = new HashMap<>();
			Map<String, Map<String, String>> valuePair = new HashMap<>();

			// Check each one props exist
			/*
			 * Use reflection to find all varialbes in global properties and
			 * global defaults and set them.
			 * 
			 * TODO migrate to an object list at some point
			 */
			Field[] values = GlobalProperties.class.getDeclaredFields();
			for (Field field : values) {
				field.setAccessible(true);
				if (field.getType().equals(String.class)) {
					String keyNameValue = field.get(GlobalProperties.class).toString();
					valueMap.put(field.getName(), keyNameValue);
				}
			}

			Field[] defaults = GlobalDefaults.class.getDeclaredFields();
			for (Field field : defaults) {
				field.setAccessible(true);
				if (field.getType().equals(String.class)) {
					Map<String, String> defaultPair = new HashMap<>();
					String fieldStr = field.getName();
					Object value = field.get(GlobalDefaults.class);
					defaultPair.put(fieldStr, value.toString());
					for (Entry<String, String> key : valueMap.entrySet()) {
						if (fieldStr.equals(key.getKey() + "_DEFAULT"))
							valuePair.put(key.getValue(), defaultPair);
					}
				}
			}

			for (Entry<String, Map<String, String>> valuePairObj : valuePair.entrySet()) {
				Set<Entry<String, String>> entrySet = valuePairObj.getValue().entrySet();
				for (Entry<String, String> entry : entrySet) {
					String propKey = valuePairObj.getKey();
					String propValue = entry.getValue();
					String globalProperty = Props.getGlobalProperty(propKey);
					if (globalProperty == null) {
						log.info("found null value of key" + propKey + ", setting default of + " + propValue);
						Props.setGlobalProperty(propKey, propValue);
					}
				}
			}

		} catch (IOException | IllegalArgumentException | IllegalAccessException e) {
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
		try (InputStream input = new FileInputStream(propFile)) {
			Properties prop = new Properties();
			prop.load(input);
			// set the properties value
			prop.put(propKey, propVal);
			// save properties to project root folder
			try (OutputStream output = new FileOutputStream(propFile)) {
				prop.store(output, null);
			}

		} catch (IOException io) {
			log.error(io);
			throw new RuntimeException("value not set for key: " + propKey + " - value: " + propVal);
		}
	}

	private static String getPropertyInternal(String propKey, String propFile) {
		try (InputStream input = new FileInputStream(propFile)) {
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
		try (InputStream input = new FileInputStream(propFile)) {
			Properties prop = new Properties();
			prop.load(input);
			// set the properties value
			prop.remove(string);
			// save properties to project root folder
			try (OutputStream output = new FileOutputStream(propFile)) {
				prop.store(output, null);
			}

		} catch (IOException io) {
			log.error(io);
			throw new RuntimeException("value not deleted for key: " + string);
		}
	}

	public static class GlobalProperties {
		public static String SORT_BY_NAME = "soryByName";
		public static String APP_NAME = "appName";
		public static String SHOW_COUNTER_OPTIONS = "showCounterOptions";
		public static String SHOW_COUTER_POPUP_EACH_START = "showCounterPopUPOnEachStart";
		public static String VERSION = "Version";
		public static String CALL_FORWARDS = "callForwards";
		public static String CALL_FORWARDS_SERVICE = "callForwardsServiceId";
		public static String NOTIFICATIONS = "notifications";
		public static String RECEPTION1WORKSTATION2BOTH0 = "reception1Workstation2Both0";
	}

	/**
	 * Names must start with the property name with default at the end
	 * 
	 * @author adamea
	 *
	 */
	public static class GlobalDefaults {
		public static String SORT_BY_NAME_DEFAULT = "true";
		public static String APP_NAME_DEFAULT = "Build";
		public static String SHOW_COUNTER_OPTIONS_DEFAULT = "true";
		public static String SHOW_COUTER_POPUP_EACH_START_DEFAULT = "false";
		public static String VERSION_DEFAULT = "1.0.0.3";
		public static String CALL_FORWARDS_DEFAULT = "false";
		public static String CALL_FORWARDS_SERVICE_DEFAULT = "1";
		public static String NOTIFICATIONS_DEFAULT = "true";
		public static String RECEPTION1WORKSTATION2BOTH0_DEFAULT = "0";
	}

}