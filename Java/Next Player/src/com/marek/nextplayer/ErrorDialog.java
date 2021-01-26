

package com.marek.nextplayer;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorDialog {
	
	private JFrame error;
	
	public ErrorDialog(String msg) {
		error = new JFrame();
		error.setTitle("Error: File is missing!");
		error.setSize(300, 100);
		error.setLocationRelativeTo(null);
		error.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel(msg, JLabel.CENTER);
		error.add(label);
		error.setVisible(true);
	}

}
