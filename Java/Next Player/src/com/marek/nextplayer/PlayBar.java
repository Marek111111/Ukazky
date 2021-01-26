

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Player;
import com.marek.nextplayer.Core.Utils;

public class PlayBar extends JPanel {
	
	private SideBar sidebar;
	private Core core;
	private Player player;
	
	private final JLabel play = new JLabel(new ImageIcon("Images/play.png"));
	
	private Timer timer;
	
	public PlayBar(final SideBar sidebar) {
		this.sidebar = sidebar;
		this.core = sidebar.getCore();
		this.player = core.getPlayer();
		
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10, 25, 12, 25));
		this.setOpaque(false);
		
		this.add(new SongProgress(player), BorderLayout.NORTH);
		
		JPanel controlBar = new JPanel();
		controlBar.setLayout(new BorderLayout());
		controlBar.setBorder(new EmptyBorder(10, 0, 0, 0));
		controlBar.setOpaque(false); 
		
		final ImageIcon shuffleIcon = new ImageIcon("Images/shuffle.png");
		final ImageIcon shuffleIcon_active = new ImageIcon("Images/shuffle_active.png");
		final JLabel shuffle = new JLabel(shuffleIcon);
		shuffle.setToolTipText("Shuffle");
		shuffle.setCursor(new Cursor(Cursor.HAND_CURSOR));
		shuffle.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(player.isShuffleOn()) {
						player.shuffleOff();
					} else {
						player.shuffleOn();
						shuffle.setIcon(shuffleIcon_active);
					}
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				shuffle.setIcon(shuffleIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				if(!player.isShuffleOn()) {
					shuffle.setIcon(shuffleIcon);
				}
			}
		});
		controlBar.add(shuffle, BorderLayout.WEST);
		
		JPanel center = new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.CENTER));
		center.setOpaque(false);
		
		final ImageIcon prevIcon = new ImageIcon("Images/previous.png");
		final ImageIcon prevIcon_active = new ImageIcon("Images/previous_active.png");
		final JLabel prev = new JLabel(prevIcon);
		prev.setToolTipText("Previous");
		prev.setCursor(new Cursor(Cursor.HAND_CURSOR));
		prev.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					player.prev();
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
		center.add(prev, BorderLayout.WEST);
		
		final ImageIcon playIcon = new ImageIcon("Images/play.png");
		final ImageIcon playIcon_active = new ImageIcon("Images/play_active.png");
		final ImageIcon pauseIcon = new ImageIcon("Images/pause.png");
		final ImageIcon pauseIcon_active = new ImageIcon("Images/pause_active.png");
		play.setToolTipText("Play/Pause");
		play.setCursor(new Cursor(Cursor.HAND_CURSOR));
		play.setBorder(new EmptyBorder(0, 15, 0, 15));
		play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					String oldAlbum;
					oldAlbum = sidebar.getPlayList().getActivePlaylist().get(0).getAlbum();
					
					/*if(player.notStarted() || player.getPlaylist().get(0).getAlbum() != oldAlbum) {
						player.setPlaylist(SideBar.getPlayList().getActivePlaylist());
						play.setIcon(new ImageIcon("Images/play.png"));
					} else*/ if(player.isPaused()) {
						player.resume();
						play.setIcon(pauseIcon);
					} else {
						player.pause();
						play.setIcon(playIcon);
					}
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				if(player.notStarted()) {
					play.setIcon(playIcon_active);
				} else if(player.isPlaying()) {
					play.setIcon(pauseIcon_active);
				} else if(player.isPaused()) {
					play.setIcon(playIcon_active);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				if(player.notStarted()) {
					play.setIcon(playIcon);
				} else if(player.isPlaying()) {
					play.setIcon(pauseIcon);
				} else if(player.isPaused()) {
					play.setIcon(playIcon);
				}
			}
		});
		center.add(play, BorderLayout.CENTER);
		
		final ImageIcon nextIcon = new ImageIcon("Images/next.png");
		final ImageIcon nextIcon_active = new ImageIcon("Images/next_active.png");
		final JLabel next = new JLabel(nextIcon);
		next.setToolTipText("Next");
		next.setCursor(new Cursor(Cursor.HAND_CURSOR));
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					player.next();
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
		center.add(next, BorderLayout.EAST);
		
		controlBar.add(center, BorderLayout.CENTER);
		
		final ImageIcon volumeIcon = new ImageIcon("Images/volume.png");
		final ImageIcon volumeIcon_active = new ImageIcon("Images/volume_active.png");
		final JLabel volume = new JLabel(volumeIcon);
		volume.setToolTipText("Volume");
		volume.setCursor(new Cursor(Cursor.HAND_CURSOR));
		final VolumeControl volumeCtrl = new VolumeControl(player, volume);
		volume.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				volume.setIcon(volumeIcon_active);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					volumeCtrl.show(e.getComponent(), 0, -100);
			}
				
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				volume.setIcon(volumeIcon);
			}
		});
		controlBar.add(volume, BorderLayout.EAST);
		
		this.add(controlBar, BorderLayout.SOUTH);
		
		player.setPlayBar(this);
	}
	
	public void setPlayIcon(ImageIcon icon) {
		play.setIcon(icon);
		play.revalidate();
		play.repaint();
	}
}

class SongProgress extends JPanel {
	
	private Timer timer;
	private JProgressBar pb;
	
	public SongProgress(final Player player) {
		this.setPreferredSize(new Dimension(200, 2));
		
		pb = new JProgressBar(JProgressBar.HORIZONTAL, 0, 0);
		
		timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(player != null && player.getSong() != null) {
					pb.setMaximum(player.getSong().getAudioDuration());
					pb.setValue(player.getProgress());
				}	
			}
		});
		
		timer.start();
		
		pb.setVisible(false);
		
		this.add(pb);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(GUI.LIGHT);
		g.fillRect(0, 0, 200, 2);
		
		g.setColor(GUI.BLUE);
		
		int progress = 0;
		int min = pb.getMinimum();
        int max = pb.getMaximum();
        int total = max - min;
		float dx = (float) (200 - 2) / (float) total;
		if (pb.getValue() == max && pb.getValue() != 0) {
            progress = 200 - 1;
        } else {
            progress = (int) (dx * pb.getValue());            
        }
		g.fillRect(0, 0, progress, 2);
	
		repaint();
	}
	
}

class VolumeControl {
	
	private JPopupMenu popup;
	private JSlider slider;
	
	public VolumeControl(final Player player, final JLabel volume) {
		 final ImageIcon volumeIcon = new ImageIcon("Images/volume.png");
		 final ImageIcon volumeIcon_active = new ImageIcon("Images/volume_active.png");
		 slider = new JSlider(0, 100, 100);
		 slider.setOrientation(JSlider.VERTICAL);
		 slider.setBackground(GUI.DARK);
		 slider.setUI(new CustomSliderUI(slider));
		 slider.addMouseListener(new MouseAdapter() {
			 	@Override
			 	public void mouseEntered(MouseEvent e) {
			 		super.mouseEntered(e);
			 		
			 		volume.setIcon(volumeIcon_active);
			 	}
			 	
	        	@Override
	        	public void mouseExited(MouseEvent e) {
	        		super.mouseExited(e);
	        		
	        		volume.setIcon(volumeIcon);
	        		popup.setVisible(false);
	        	}
	         });
		 slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				player.setVolume(slider.getValue()/100f);
			}
		 });
         
         popup = new JPopupMenu();
         popup.setBorder(new EmptyBorder(0, 0, 0, 0));
         popup.setPopupSize(16, 100);
         popup.setLayout(new BorderLayout());
         popup.add(slider);
	}
	
	public void show(Component c, int x, int y) {
		popup.show(c, x, y);
	}
	
}
