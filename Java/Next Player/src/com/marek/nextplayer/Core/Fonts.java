

package com.marek.nextplayer.Core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;

public class Fonts {
	
	private static Font font;
	
	public static Font light(float size) {
		try {
			font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("Data/Fonts/SourceSansPro-Light.ttf"));
			return font.deriveFont(size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return new JLabel().getFont().deriveFont(size);
	}
	
	public static Font regular(float size) {
		try {
			font = Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("Data/Fonts/SourceSansPro-Regular.ttf"));
			return font.deriveFont(size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return new JLabel().getFont().deriveFont(size);
	}
	
	public static Font semibold(float size) {
		try {
			font = Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("Data/Fonts/SourceSansPro-Semibold.ttf"));
			return font.deriveFont(size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return new JLabel().getFont().deriveFont(size);
	}
	
	public static Font bold(float size) {
		try {
			font = Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("Data/Fonts/SourceSansPro-Bold.ttf"));
			return font.deriveFont(size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return new JLabel().getFont().deriveFont(size);
	}

}
