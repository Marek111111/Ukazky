

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
