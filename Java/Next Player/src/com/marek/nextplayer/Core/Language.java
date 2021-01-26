package com.marek.nextplayer.Core;

public class Language {
	
	public static final String PLAY = "PLAY";
	
	public static String getString(String value) {
		return loadString(value);
	}
	
	private static String loadString(String value) {
		String text = null;
		// Variables.get(Variables.LANGUAGE) return "CZECH"
		// "CZECH" add ".txt"
		// text = getLanguageFile("Languages/CZECH.txt").getString(value);
		
		return text;
	}

}
