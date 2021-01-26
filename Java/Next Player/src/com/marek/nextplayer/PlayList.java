

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Album;
import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Fonts;
import com.marek.nextplayer.Core.Song;
import com.marek.nextplayer.Core.Utils;

public class PlayList extends JPanel {
	
	private SideBar sidebar;
	private Core core;
	
	private Album album;
	private SongLink songLink;
	private LinkedList<SongLink> songList = new LinkedList<SongLink>();
	private int order = 0;
	
	public PlayList(SideBar sidebar, Album album) {
		this.sidebar = sidebar;
		this.core = sidebar.getCore();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(5, 0, 0, 0));
		this.setBackground(GUI.DARK);
		
		setActivePlaylist(album);
	}
	
	public void setActivePlaylist(Album album) {
		this.album = album;
		
		this.removeAll();
		order = 0;
		if(!album.getSongs().isEmpty()) {
			for(Song song : album.getSongs()) {
				songList.add(new SongLink(sidebar, song, order));
				order += 1;
			}
			
			for(SongLink sl : songList) {
				this.add(sl);
			}
			
			songList.clear();
			order = 0;
		} else {
			this.add(new SongLink("Playlist is empty."));
		}
		this.revalidate();
		this.repaint();
	}
	
	public LinkedList<Song> getActivePlaylist() {
		return album.getSongs();
	}

}

class SongLink extends JPanel {
	
	private SideBar sidebar;
	private Core core;
	
	private JPanel center;
	private JLabel playInidicator;
	private ImageIcon playIndiGif;
	private ImageIcon playIndiGifPaused;
	private ImageIcon playIndiGifEmpty;
	private JLabel songName;
	private JLabel songDuration;
	
	public SongLink(final SideBar sidebar, final Song song, final int order) {
		this.sidebar = sidebar;
		this.core = sidebar.getCore();
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(220, 27));
		this.setMaximumSize(new Dimension(220, 27));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					center.setBackground(new Color(75, 75, 75));
					core.getPlayer().setPlaylist(sidebar.getPlayList().getActivePlaylist());
					core.getPlayer().setTrack(core.getPlayer().getPlaylist().get(order));
					if(order - 1 == 0) {
						core.getPlayer().track = 0;
					} else {
						core.getPlayer().track = order;
					}
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				center.setBackground(new Color(75, 75, 75));
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				super.mouseExited(arg0);
				center.setBackground(GUI.DARK);
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				super.mousePressed(arg0);
				center.setBackground(new Color(90, 90, 90));
			}
		});
		this.setOpaque(false);
		
		this.songName = new JLabel(String.valueOf(order + 1) + ". " + splitString(song.getName(), 20));
		this.songName.setFont(Fonts.semibold(14));
		this.songName.setForeground(GUI.LIGHT);
		
		int min = (song.getAudioDuration() / 1000) / 60;
		int sec = (song.getAudioDuration() / 1000) % 60;
	    if(sec < 10) {
	    	this.songDuration = new JLabel(min + ":" + 0 + sec);
	    } else {
	    	this.songDuration = new JLabel(min + ":" + sec);
	    }
		this.songDuration.setFont(Fonts.semibold(14));
		this.songDuration.setForeground(GUI.LIGHT);
		
		center = new JPanel();
		center.setLayout(new BorderLayout());
		center.setPreferredSize(new Dimension(200, 25));
		center.setBorder(new EmptyBorder(0, 5, 0, 5));
		center.setCursor(new Cursor(Cursor.HAND_CURSOR));
		center.setBackground(GUI.DARK);
		center.add(songName, BorderLayout.WEST);
		center.add(songDuration, BorderLayout.EAST);
		
		this.playIndiGif = new ImageIcon("Images/play-indicator.gif");
		this.playIndiGifPaused = new ImageIcon("Images/play-indicator.png");
		this.playIndiGifEmpty = new ImageIcon("Images/play-indicator-empty.png");
		this.playInidicator = new JLabel(playIndiGifEmpty);
		this.playInidicator.setBorder(new EmptyBorder(0, 3, 3, 4));
		this.playInidicator.setVerticalAlignment(JLabel.CENTER);
		this.playInidicator.setDoubleBuffered(true);
		Timer timer = new Timer(300, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(core.getPlayer().getSong() != null)
					if(core.getPlayer().getSong().getName() == song.getName()){
						if(core.getPlayer().isPlaying()) {
							if(playInidicator.getIcon() != playIndiGif);
								playInidicator.setIcon(playIndiGif);
						} else if(core.getPlayer().isPaused()) {
							if(playInidicator.getIcon() != playIndiGifPaused);
								playInidicator.setIcon(playIndiGifPaused);
						} else if(core.getPlayer().notStarted()) {
							if(playInidicator.getIcon() != playIndiGifEmpty);
								playInidicator.setIcon(playIndiGifEmpty);
						}
					} else {
						if(playInidicator.getIcon() != playIndiGifEmpty);
							playInidicator.setIcon(playIndiGifEmpty);
					}
			}
		});
		timer.start();
		
		this.add(playInidicator, BorderLayout.WEST);
		this.add(center, BorderLayout.CENTER);
	}
	
	public String splitString(String input, int maxStringLenght) {
	    StringBuilder output = new StringBuilder(input);
	    if (input.length() > maxStringLenght) {
	    	output.delete(maxStringLenght, input.length());
	    	output.append("...");
	    }
	    
	    return output.toString();
	}
	
	public SongLink(String string) {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		this.add(new JLabel(string), BorderLayout.CENTER);
	}
	
}
