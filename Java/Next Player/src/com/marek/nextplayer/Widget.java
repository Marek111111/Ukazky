/*	  Next core.getPlayer() - The next music core.getPlayer()
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
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Fonts;
import com.marek.nextplayer.Core.Song;
import com.marek.nextplayer.Core.Utils;
import com.marek.nextplayer.Core.Variables;

public class Widget {
	
	private Core core;
	
	private JFrame frame;
	
	private BufferedImage buffImage;
	private Image image;
	private JLabel cover;
	
	private JPanel controls;
	private JLabel close;
	private JLabel minimize;
	
	private JPanel songPanel;
	private JLabel songName;
	private JLabel songArtist;
	
	private JPanel barPanel;
	private JLabel prev;
	private JLabel play;
	private JLabel next;
	
	private Point initialClick;
	
	public Widget(final Core core) {
		this.core = core;
		
		cover = new JLabel();
				
		frame = new JFrame();
		frame.setContentPane(cover);
		frame.setIconImage(new ImageIcon("Icon.png").getImage());
		frame.setSize(200, 200);
		frame.setLocation(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width-frame.getWidth(), GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height-frame.getHeight());
		frame.setAlwaysOnTop(Variables.get(Variables.WIDGET_MODE_ALWAYS_ON_TOP).isTrue());
		frame.setUndecorated(true);
		frame.setLayout(new BorderLayout());
		frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
                frame.getComponentAt(initialClick);
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                int thisX = frame.getLocation().x;
                int thisY = frame.getLocation().y;

                int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
                int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

                int X = thisX + xMoved;
                int Y = thisY + yMoved;

                frame.setLocation(X, Y);
            }
        });
        
        frame.add(new Controls(core, true, false, false), BorderLayout.NORTH);
        
        barPanel = new JPanel();
        barPanel.setLayout(new BorderLayout());
		barPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		barPanel.setOpaque(false);
		
		JPanel prevPanel = new JPanel();
		prevPanel.setBorder(new EmptyBorder(50, 20, 0, 0));
		prevPanel.setOpaque(false);
		
		final ImageIcon prevIcon = new ImageIcon("Images/previous_widget.png");
		final ImageIcon prevIcon_active = new ImageIcon("Images/previous_widget_active.png");
		prev = new JLabel(prevIcon);
		prev.setToolTipText("Previous");
		prev.setCursor(new Cursor(Cursor.HAND_CURSOR));
		prev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getPlayer().prev();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				prev.setIcon(prevIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				prev.setIcon(prevIcon);
			}
		});
		prevPanel.add(prev);
		barPanel.add(prevPanel, BorderLayout.WEST);
		
		JPanel playPanel = new JPanel();
		playPanel.setBorder(new EmptyBorder(42, 0, 0, 0));
		playPanel.setOpaque(false);
		
		final ImageIcon playIcon = new ImageIcon("Images/play_widget.png");
		final ImageIcon pauseIcon = new ImageIcon("Images/pause_widget.png");
		final ImageIcon playIcon_active = new ImageIcon("Images/play_widget_active.png");
		final ImageIcon pauseIcon_active = new ImageIcon("Images/pause_widget_active.png");
		play = new JLabel(playIcon);
		play.setToolTipText("Play/Pause");
		play.setCursor(new Cursor(Cursor.HAND_CURSOR));
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					String oldAlbum;
					oldAlbum = core.getMainFrame().getSideBar().getPlayList().getActivePlaylist().get(0).getAlbum();
					
					if(core.getPlayer().notStarted() || core.getPlayer().getPlaylist().get(0).getAlbum() != oldAlbum) {
						core.getPlayer().setPlaylist(core.getMainFrame().getSideBar().getPlayList().getActivePlaylist());
						play.setIcon(playIcon);
					} else if(core.getPlayer().isPaused()) {
						core.getPlayer().resume();
						play.setIcon(pauseIcon);
					} else {
						core.getPlayer().pause();
						play.setIcon(playIcon);
					}
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				if(core.getPlayer().notStarted()) {
					play.setIcon(playIcon_active);
				} else if(core.getPlayer().isPlaying()) {
					play.setIcon(pauseIcon_active);
				} else if(core.getPlayer().isPaused()) {
					play.setIcon(playIcon_active);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				if(core.getPlayer().notStarted()) {
					play.setIcon(playIcon);
				} else if(core.getPlayer().isPlaying()) {
					play.setIcon(pauseIcon);
				} else if(core.getPlayer().isPaused()) {
					play.setIcon(playIcon);
				}
			}
		});
		playPanel.add(play);
		barPanel.add(playPanel, BorderLayout.CENTER);
		
		JPanel nextPanel = new JPanel();
		nextPanel.setBorder(new EmptyBorder(50, 0, 0, 20));
		nextPanel.setOpaque(false);
		
		final ImageIcon nextIcon = new ImageIcon("Images/next_widget.png");
		final ImageIcon nextIcon_active = new ImageIcon("Images/next_widget_active.png");
		next = new JLabel(nextIcon);
		next.setToolTipText("Next");
		next.setCursor(new Cursor(Cursor.HAND_CURSOR));
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getPlayer().next();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				next.setIcon(nextIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				next.setIcon(nextIcon);
			}
		});
		nextPanel.add(next);
		barPanel.add(nextPanel, BorderLayout.EAST);
		
		frame.add(barPanel, BorderLayout.CENTER);
		
		songPanel = new JPanel();
		songPanel.setLayout(new BorderLayout());
		songPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		songPanel.setOpaque(false);
		
		songName = new JLabel();
		songName.setFont(Fonts.regular(22));
		songPanel.add(songName, BorderLayout.NORTH);
		
		songArtist = new JLabel();
		songArtist.setFont(Fonts.regular(16));
		songPanel.add(songArtist, BorderLayout.SOUTH);
		
		frame.add(songPanel, BorderLayout.SOUTH);
	}
	
	public void setSong(Song song) {
		frame.setTitle("Next core.getPlayer() - " + song.getArtist() + " - " + song.getName());
		
		try {
			buffImage = ImageIO.read(new File(song.getCoverPath()));
			image = buffImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cover.setIcon(new ImageIcon(image));
		
		songName.setText(song.getName());
		songName.setToolTipText(song.getName());
		songName.setForeground(Utils.getContrastColor(Utils.getImageColor(buffImage, songName.getX(), songName.getY())));
		
		songArtist.setText(song.getArtist());
		songArtist.setToolTipText(song.getArtist());
		songArtist.setForeground(Utils.getContrastColor(Utils.getImageColor(buffImage, songArtist.getX(), songArtist.getY())));
		
		frame.revalidate();
		frame.repaint();
	}
	
	public void show(Song song) {
		frame.setTitle("Next core.getPlayer() - " + song.getArtist() + " - " + song.getName());
		
		try {
			buffImage = ImageIO.read(new File(song.getCoverPath()));
			image = buffImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cover.setIcon(new ImageIcon(image));
		
		songName.setText(song.getName());
		songName.setToolTipText(song.getName());
		songName.setForeground(Utils.getContrastColor(Utils.getImageColor(buffImage, songName.getX(), songName.getY())));
		
		songArtist.setText(song.getArtist());
		songArtist.setToolTipText(song.getArtist());
		songArtist.setForeground(Utils.getContrastColor(Utils.getImageColor(buffImage, songArtist.getX(), songArtist.getY())));
		
		frame.revalidate();
		frame.repaint();
		
		if(core.getPlayer().notStarted())
			setSong(core.getMainFrame().getSideBar().getPlayList().getActivePlaylist().get(0));
		
		if(core.getPlayer().isPlaying()) {
			setPlayIcon(new ImageIcon("Images/pause_widget.png"));
			setSong(core.getPlayer().getSong());
		}
		
		if(core.getPlayer().isPaused()) {
			setSong(core.getMainFrame().getSideBar().getPlayList().getActivePlaylist().get(0));
			setPlayIcon(new ImageIcon("Images/play_widget.png"));
		}
		
		frame.setAlwaysOnTop(Variables.get(Variables.WIDGET_MODE_ALWAYS_ON_TOP).isTrue());
		frame.setVisible(true);
	}
	
	public void setPlayIcon(ImageIcon icon) {
		play.setIcon(icon);
	}
	
	public void minimize() {
		frame.setState(JFrame.ICONIFIED);
	}
	
	public void close() {
		frame.setVisible(false);
	}
}
