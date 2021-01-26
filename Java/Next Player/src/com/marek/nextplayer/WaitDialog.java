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
