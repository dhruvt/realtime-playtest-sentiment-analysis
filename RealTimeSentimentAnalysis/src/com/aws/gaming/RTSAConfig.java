package com.aws.gaming;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Singleton helper class that holds all the configuration values for the RTSA system
 * 
 * @author dhruv
 *
 */
public class RTSAConfig {
	
	private Properties configFile;

	private static RTSAConfig instance;

	private RTSAConfig() {
		configFile = new java.util.Properties();
		try {
			configFile.load(new FileInputStream("./config.properties"));
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}

	private String getValue(String key) {
		return configFile.getProperty(key);
	}

	public static String getProperty(String key) {
		if (instance == null) instance = new RTSAConfig();
			return instance.getValue(key);
	}

}
