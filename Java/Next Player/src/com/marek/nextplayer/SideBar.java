

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import com.marek.nextplayer.Core.Album;
import com.marek.nextplayer.Core.Core;

public class SideBar extends JPanel {
	
	private Core core;
	
	private JPanel albumPanel;
	private AlbumMetadata metaData;
	private PlayList playList;
	private PlayBar playbar;
	
	private Point initialClick;
	
	private JScrollPane scrollPane;

	public SideBar(final Core core) {
		this.core = core;
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(250, 600));
		this.setBackground(GUI.DARK);
		this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                getComponentAt(initialClick);
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                int thisX = core.getMainFrame().getFrame().getLocation().x;
                int thisY = core.getMainFrame().getFrame().getLocation().y;

                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                int X = thisX + xMoved;
                int Y = thisY + yMoved;

                core.getMainFrame().getFrame().setLocation(X, Y);
            }
        });
		
		this.add(new Controls(core, false, false, false), BorderLayout.NORTH);
		
		albumPanel = new JPanel();
		albumPanel.setLayout(new BorderLayout());
		albumPanel.setOpaque(false);
		
		Album album = core.getLibraryManager().getLibrary().getAlbums().getFirst();
		metaData = new AlbumMetadata(this, new AlbumSlot(this, album, true));
		
		albumPanel.add(metaData, BorderLayout.NORTH);
		
		playList = new PlayList(this, album);
		
		scrollPane = new JScrollPane(playList);
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 10));
		scrollPane.setOpaque(false);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar(false));
		scrollPane.getVerticalScrollBar().setBackground(GUI.DARK);
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(3, 10));
		scrollPane.getVerticalScrollBar().setCursor(new Cursor(Cursor.HAND_CURSOR));
		scrollPane.getVerticalScrollBar().setUnitIncrement(6);
		albumPanel.add(scrollPane, BorderLayout.CENTER);
		this.add(albumPanel, BorderLayout.CENTER);
		
		this.playbar = new PlayBar(this);
		this.add(playbar, BorderLayout.SOUTH);
	}
	
	public void refresh() {
		albumPanel.remove(metaData);
		
		Album album = core.getLibraryManager().getLibrary().getAlbums().getFirst();
		metaData = new AlbumMetadata(this, new AlbumSlot(this, album, true));
		albumPanel.add(metaData, BorderLayout.NORTH);
		
		playList = new PlayList(this, album);
		playList.revalidate();
		scrollPane.setViewportView(playList);
		scrollPane.revalidate();
		
		albumPanel.add(scrollPane, BorderLayout.CENTER);
		albumPanel.revalidate();
		
		this.add(albumPanel, BorderLayout.CENTER);
		this.revalidate();
	}
	
	public void scrollTo(int value) {
		scrollPane.getVerticalScrollBar().setValue(27*value);
	}
	
	public AlbumMetadata getAlbumMetadata() {
		return metaData;
	}
	
	public PlayList getPlayList() {
		return playList;
	}
	
	public Core getCore() {
		return core;
	}
	
}
