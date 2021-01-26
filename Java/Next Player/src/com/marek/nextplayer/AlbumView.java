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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Album;
import com.marek.nextplayer.Core.Core;

public class AlbumView extends JPanel {
	
	private Core core;
	private SideBar sidebar;
	
	public AlbumView(SideBar sidebar, JScrollPane pane) {
		this.sidebar = sidebar;
		this.core = sidebar.getCore();
		
		this.setLayout(new WrapLayout(WrapLayout.LEADING, 53, 30, 774));
		this.setBorder(new EmptyBorder(0, -23, 0, 0));
		this.setBackground(GUI.LIGHT);
		
		for(Album a : core.getLibraryManager().getLibrary().getAlbums()) {
			this.add(new AlbumSlot(sidebar, a, false));
		}
		
		this.add(new AlbumSlotCustom(sidebar.getCore()));
	}
	
	public void refreshAlbumView() {
		this.removeAll();
		
		for(Album a : core.getLibraryManager().getLibrary().getAlbums()) {
			this.add(new AlbumSlot(sidebar, a, false));
		}
		
		this.add(new AlbumSlotCustom(sidebar.getCore()));
		
		this.revalidate();
	}
}
