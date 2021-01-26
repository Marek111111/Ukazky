

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
