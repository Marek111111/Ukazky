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

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Fonts;

public class About {
	
	private JDialog frame;
	
	public About() {
		frame = new JDialog();
		frame.setTitle("Next Player - About");
		frame.setIconImage(new ImageIcon("icon.png").getImage());
		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addComponents();
	}
	
	private void addComponents() {
		frame.setLayout(new BorderLayout());
		frame.getRootPane().setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BorderLayout());
		
		JLabel name = new JLabel("Next Player", JLabel.CENTER);
		name.setFont(Fonts.semibold(26));
		namePanel.add(name, BorderLayout.NORTH);

		JLabel version = new JLabel("Version RC 1.0", JLabel.CENTER);
		version.setFont(Fonts.regular(18));
		namePanel.add(version, BorderLayout.SOUTH);
		
		frame.add(namePanel, BorderLayout.NORTH);
		
		JPanel copyrightPanel = new JPanel();
		copyrightPanel.setLayout(new BorderLayout());
		
		JLabel copyright = new JLabel("<html><center>Copyright (c) Hall Software 2014 <br> All rights reserved!<center></html>", JLabel.CENTER);
		copyright.setFont(Fonts.regular(16));
		copyrightPanel.add(copyright, BorderLayout.CENTER);
		
		frame.add(copyrightPanel, BorderLayout.CENTER);

		JPanel disclaimerPanel = new JPanel();
		disclaimerPanel.setLayout(new BorderLayout());
		
		JLabel disclaimer = new JLabel("<html><center>This product was built by using Java and Java libraries: <br> "
										+ "beaglebuddy_mp3.jar, jl1.0.1.jar, mp3spi1.9.5.jar, tritonus_share.jar. <br>"
										+ "Java and all Java-based marks are trademarks or registered trademarks of Sun Microsystems, Inc. in the U. S. and other countries. "
										+ "All other company and/or product names are the property of their respective owners. <br>"
										+ "For more information about license please read LICENSE.txt file distributed with this program.<center></html>", JLabel.CENTER);
		disclaimer.setFont(Fonts.regular(12));
		disclaimerPanel.add(disclaimer, BorderLayout.CENTER);
		
		frame.add(disclaimerPanel, BorderLayout.SOUTH);
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

}
