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
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Fonts;
import com.marek.nextplayer.Core.Variables;

public class MusicDirDialog {
	
	private Core core;

	private JFrame frame;
	
	private JFileChooser chooser;
	
	private boolean directToFileChooser;
	
	public MusicDirDialog(Core core) {
		this.core = core;
		
		frame = new JFrame("Next Player");
		frame.setIconImage(new ImageIcon("Icon.png").getImage());
		frame.setSize(350, 130);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		
		chooser = new JFileChooser(Variables.MUSIC_PATH);
		chooser.setDialogTitle("Select your music folder.");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		addComponents(frame.getContentPane());
	}
	
	private void addComponents(Container c) {
		frame.getRootPane().setBorder(BorderFactory.createCompoundBorder(new LineBorder(GUI.DARK), new EmptyBorder(10, 10, 10, 10)));
		frame.setBackground(GUI.LIGHT);
		c.setBackground(GUI.LIGHT);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setOpaque(false);
		
		JLabel noMusicFound = new JLabel("Your music library is empty.", JLabel.CENTER);
		noMusicFound.setFont(Fonts.semibold(18));
		noMusicFound.setForeground(GUI.DARK);
		panel.add(noMusicFound, BorderLayout.NORTH);
		
		JPanel controls = new JPanel();
		controls.setLayout(new BorderLayout());
		controls.setBorder(new EmptyBorder(0, 20, 0, 20));
		controls.setOpaque(false);
		
		final JLabel changeMusicDir = new JLabel("Select music folder", JLabel.CENTER);
		changeMusicDir.setFont(Fonts.regular(18));
		changeMusicDir.setForeground(GUI.BLUE);
		changeMusicDir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		changeMusicDir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					showFileDialog();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				changeMusicDir.setForeground(GUI.DARK);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				changeMusicDir.setForeground(GUI.BLUE);
			}
		});
		controls.add(changeMusicDir, BorderLayout.WEST);
		
		final JLabel close = new JLabel("Close", JLabel.CENTER);
		close.setFont(Fonts.regular(18));
		close.setForeground(GUI.BLUE);
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					close();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				close.setForeground(GUI.DARK);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				close.setForeground(GUI.BLUE);
			}
		});
		controls.add(close, BorderLayout.EAST);
		
		panel.add(controls, BorderLayout.SOUTH);
		
		c.add(panel);
	}
	
	private void showFileDialog() {
		int returnVal = chooser.showOpenDialog(frame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			Variables.set(Variables.get(Variables.MUSIC_PATH), chooser.getSelectedFile().getAbsolutePath());
			core.refreshMusicLibrary();
		} else {
			chooser.cancelSelection();
		}
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
	public void close() {
		core.stopCore();
		frame.dispose();
	}
	
}
