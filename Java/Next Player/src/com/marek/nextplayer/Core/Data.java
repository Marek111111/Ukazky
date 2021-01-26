package com.marek.nextplayer.Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Data {
	
	private File dataDir = new File(System.getProperty("user.home") + "\\AppData\\Local\\Hall Software\\Next Player\\");
	private Gson json;
	
	public Data() {
		json = new GsonBuilder().create();
	}
	
	public void writeData(String fileName, Object object) {
		try {  
			FileWriter writer = new FileWriter(dataDir + "\\" + fileName + ".json");  
			writer.write(json.toJson(object));
			writer.close();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}
	}
	
	public Object readData(String fileName, Object object) {
		FileReader fr = null;
		BufferedReader br = null;
		Object objectToLoad = null;
		
		if(new File(dataDir + "\\" + fileName + ".json").exists()) {
			try {
				fr = new FileReader(dataDir + "\\" + fileName + ".json");
				br = new BufferedReader(fr);
				objectToLoad = json.fromJson(br, object.getClass());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
		}		     
		
		return objectToLoad;
	}

}
