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

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Album;
import com.marek.nextplayer.Core.Core;

public class AlbumMetadata extends JPanel {
	
	private SideBar sidebar;
	private Core core;
	
	private static Album album;

	public AlbumMetadata(SideBar sidebar, AlbumSlot albumSlot) {
		this.sidebar = sidebar;
		this.core = sidebar.getCore();
		
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 27, 10, 27));
		this.setOpaque(false);
		
		this.add(new AlbumSlot(sidebar, core.getLibraryManager().getLibrary().getAlbums().getFirst(), true), BorderLayout.CENTER);
	}
	
	public void setActiveAlbum(Album album) {
		sidebar.getPlayList().setActivePlaylist(core.getLibraryManager().getLibrary().getAlbum(album.getName()));
		
		this.removeAll();
		this.add(new AlbumSlot(sidebar, album, true), BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
}
