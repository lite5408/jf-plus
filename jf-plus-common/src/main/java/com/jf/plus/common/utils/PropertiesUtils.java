package com.jf.plus.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtils
 * 
 * @author tangyh 2014-09-15
 * 
 */
public class PropertiesUtils {

	//common-config.properties 公共配置
	private static final String CONFIG_FILE1 = "/webser/xxh/config/xxh-common-config.properties";
	private static final String CONFIG_FILE2 = "/webser/xxh/config/xxh-common-config.properties";
	private static PropertiesUtils self = new PropertiesUtils();

	private Properties props = new Properties();

	private PropertiesUtils() {
		InputStream in = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE1);
			props.load(in);
		} catch (Exception e) {
			try {
				in = new BufferedInputStream(new FileInputStream(CONFIG_FILE2));
				props.load(in);
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new RuntimeException(e);
			}
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getValue(String key) {
		return props.getProperty(key);
	}

	public static PropertiesUtils instance() {
		return self;
	}
}
