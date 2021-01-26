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
