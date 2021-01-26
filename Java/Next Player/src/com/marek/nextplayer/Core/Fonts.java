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
