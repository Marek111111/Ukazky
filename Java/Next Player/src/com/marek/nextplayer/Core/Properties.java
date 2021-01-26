/*	  Next Player - The next music player
 *    Copyright (C) 2014  Hall Software
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
