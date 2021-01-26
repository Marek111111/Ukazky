

package com.marek.nextplayer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.marek.nextplayer.Core.Core;

public class NextPlayer {
	
	public static void main(String[] args) {	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		System.setProperty("awt.useSystemAAFontSettings","on"); 
		System.setProperty("swing.aatext", "true");
		
		System.out.println("App started!");
		new Core().run();
	}

}
