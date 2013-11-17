package com.summercrow.spacetip.cliente;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesSpaceTip {
	
	private static PropertiesSpaceTip me;
	private Properties properties;
	
	private PropertiesSpaceTip(){
		properties = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("spacetip.properties");
		
		try {
			InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
			properties.load(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static PropertiesSpaceTip getInstance(){
		if(me == null){
			me = new PropertiesSpaceTip();
		}
		return me;
	}
	
	public String getProperty(String name){
		return properties.getProperty(name);
	}

}
