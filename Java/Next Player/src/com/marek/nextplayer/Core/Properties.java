

package com.marek.nextplayer.Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Properties {
	
	private static java.util.Properties prop;
	private final static File config = new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software\\Next Player\\config.ini");
	private static FileInputStream in;
	private static FileOutputStream out;
	
	public static void write(String key, String value) {
		if(new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software\\Next Player").exists()) {
			if(config.exists()) {
				try {
					in = new FileInputStream(config);
					out = new FileOutputStream(config);
					
					prop.load(in);
					prop.setProperty(key, value);
					prop.store(out, "");
					
					in.close();
					out.close();
				} catch (IOException e) {
					System.out.println("Cannot write properties to config.ini!");
					e.printStackTrace();
				}
			} else {
				try {
					config.createNewFile();
					write(key, value);
				} catch (IOException e) {
					System.out.println("Cannot write properties to config.ini!");
					e.printStackTrace();
				}
			}
		} else {
			new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software").mkdir();
			new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software\\Next Player").mkdir();
			write(key, value);
		}
	}
	
	public static String read(String key) {
		prop = new java.util.Properties();
		
		if(new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software\\Next Player").exists()) {
			if(config.exists()) {
				try {
					in = new FileInputStream(config);
					
					prop.load(in);
					in.close();
					
					return prop.getProperty(key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					config.createNewFile();
					read(key);
				} catch (IOException e) {
					System.out.println("Cannot read properties from config.ini!");
					e.printStackTrace();
				}
			}
		} else {
			new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software").mkdir();
			new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software\\Next Player").mkdir();
			read(key);
		}
		return null;
	}
}
