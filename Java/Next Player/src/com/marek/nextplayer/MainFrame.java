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
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Variables;

public class MainFrame {
	
	private JFrame frame;
	
	private Core core;
	
	private SideBar sidebar;
	
	private AlbumView albumView;
	private JScrollPane scrollPane;
	
	public MainFrame(Core core) {
		this.core = core;
		
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setTitle("Next Player");
		frame.setSize(1024, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setIconImage(new ImageIcon("Icon.png").getImage());
		
		addComponents(frame.getContentPane());
		
		frame.setVisible(true);
	}
	
	private void addComponents(Container c) {
		sidebar = new SideBar(core);
		c.add(sidebar, BorderLayout.EAST);
		
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(774, 600));
		scrollPane.setMaximumSize(new Dimension(774, 600));
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar(true));
		scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 10));
		scrollPane.getVerticalScrollBar().setCursor(new Cursor(Cursor.HAND_CURSOR));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(6);
		
		albumView = new AlbumView(sidebar, scrollPane);
		scrollPane.setViewportView(albumView);
		
		c.add(scrollPane, BorderLayout.WEST);
	}
	
	public void minimize() {
		frame.setState(JFrame.ICONIFIED);
	}
	
	public void simpleMode(boolean on) {
		boolean full = Variables.get(Variables.SIMPLE_MODE_FULL).isTrue();
		boolean alwaysOnTop = Variables.get(Variables.SIMPLE_MODE_ALWAYS_ON_TOP).isTrue();
		
		if(on) {
			if(full) {
				frame.setSize(250, GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
				frame.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width - 250, 0);
			} else {
				frame.setSize(250, 600);
				frame.setLocationRelativeTo(null);
			}
			frame.remove(scrollPane);
			frame.setAlwaysOnTop(alwaysOnTop);
			frame.revalidate();
		} else {
			frame.setSize(1024, 600);
			addScrollPane();
			frame.setLocationRelativeTo(null);
			frame.setAlwaysOnTop(false);
			frame.revalidate();
		}
	}
	
	private void addScrollPane() {
		frame.add(scrollPane, BorderLayout.WEST);
		frame.revalidate();
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}
	
	public SideBar getSideBar() {
		return sidebar;
	}
	
	public AlbumView getAlbumView() {
		return albumView;
	}
	
	public JFrame getFrame() {
		return frame;
	}

}
