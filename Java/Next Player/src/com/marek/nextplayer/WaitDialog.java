

package com.marek.nextplayer;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.marek.nextplayer.Core.Fonts;

public class WaitDialog {
	
	private JDialog wait;
	private JLabel label;
	
	public WaitDialog() {
		wait = new JDialog();
		wait.setTitle("Next Player");
		wait.setSize(300, 100);
		wait.setLocationRelativeTo(null);
		wait.setUndecorated(true);
		wait.getContentPane().setBackground(GUI.LIGHT);
		wait.getRootPane().setBorder(new LineBorder(GUI.DARK));
		wait.getRootPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
		wait.setAlwaysOnTop(true);
		
		label = new JLabel("Loading your music...", JLabel.CENTER);
		label.setFont(Fonts.semibold(18));
		label.setForeground(GUI.DARK);
		wait.add(label);
	}
	
	public void show(String waitText) {
		label.setText(waitText);
		wait.setVisible(true);
	}
	
	public void hide() {
		wait.setVisible(false);
	}

}
