

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Album;
import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Fonts;

public class AlbumSlot extends JPanel {
	
	private SideBar sidebar;
	private Core core;
	
	private JLabel albumName;
	private JLabel albumArtist;
	private BufferedImage albumCover;
	private Image image;
	private JLabel cover;
	
	private EditAlbumDialog editDialog;
	
	public AlbumSlot(final SideBar sidebar, final Album album, boolean inSideBar) {
		this.sidebar = sidebar;
		this.core = sidebar.getCore();
		this.editDialog = core.getEditAlbumDialog();
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 280));
		this.setOpaque(false);
		
		this.albumName = new JLabel(("<html><center>" + shortenText(addLinebreaks(album.getName(), 20), 40) + "<center>"), JLabel.CENTER);
		this.albumName.setToolTipText(album.getName());
		this.albumName.setBorder(new EmptyBorder(13, 0, 0, 0));
		this.albumName.setFont(Fonts.semibold(18));
		if(inSideBar) {
			this.albumName.setForeground(GUI.LIGHT);
		} else {
			this.albumName.setForeground(GUI.DARK);
		}
	
		this.albumArtist = new JLabel(("<html><center>" + shortenText(addLinebreaks(album.getArtist(), 25), 50) + "<center>"), JLabel.CENTER);
		this.albumArtist.setToolTipText(album.getArtist());
		this.albumArtist.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.albumArtist.setFont(Fonts.regular(16));
		this.albumArtist.setForeground(GUI.MEDIUM);
		
		if(album.getCoverPath() != null) {
			if(new File(album.getCoverPath()).exists()) {
				try {
					this.albumCover = ImageIO.read(new File(album.getCoverPath()));
					this.image = albumCover.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				} catch (IOException e) {
					System.out.println(album.getCoverPath());
					e.printStackTrace();
				}
			} else {
				try {
					this.albumCover = ImageIO.read(new File("Images/no-cover.png"));
					this.image = albumCover.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				this.albumCover = ImageIO.read(new File("Images/no-cover.png"));
				this.image = albumCover.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		final ImageIcon coverIcon = new ImageIcon(image);
		this.cover = new JLabel(coverIcon);
		this.cover.setPreferredSize(new Dimension(200, 200));
		if(!inSideBar) {
			cover.setCursor(new Cursor(Cursor.HAND_CURSOR));
			cover.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);
					
					if(e.getButton() == MouseEvent.BUTTON1)
						sidebar.getAlbumMetadata().setActiveAlbum(core.getLibraryManager().getLibrary().getAlbum(album.getName()));
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);
					
					cover.setIcon(new ImageIcon(image.getScaledInstance(190, 190, Image.SCALE_SMOOTH)));
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					
					cover.setIcon(coverIcon);
				}
			});
		}
	
		if(inSideBar) {
			final JLayeredPane pane = new JLayeredPane();
			pane.setPreferredSize(new Dimension(200, 200));
			pane.setOpaque(false);
			
			final ImageIcon edit = new ImageIcon("Images/edit.png");
			final ImageIcon edit_active = new ImageIcon("Images/edit_active.png");
			final JLabel button = new JLabel(edit);
			button.setBounds((200/2)-(edit.getIconWidth()/2), (200/2)-(edit.getIconHeight()/2), edit.getIconWidth(), edit.getIconHeight());
			button.setVisible(false);
			pane.add(button, 0);
			
			cover.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);
					
					button.setVisible(true);
					cover.setIcon(new ImageIcon(image){
						@Override
						public synchronized void paintIcon(Component c, Graphics g,	int x, int y) {
							super.paintIcon(c, g, x, y);
							new ImageIcon("Images/album-edit_overlay.png").paintIcon(c, g, x, y);
						}
					});
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					
					button.setVisible(false);
					cover.setIcon(new ImageIcon(image));
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);
					
					int x = e.getX();
					int y = e.getY();
					
					if(e.getButton() == MouseEvent.BUTTON1)
						if(x >= button.getX() && x <= button.getX()+button.getWidth() && y >= button.getY() && y <= button.getY()+button.getHeight()) {
							edit(album);
						}
				}
			});
			cover.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					super.mouseMoved(e);
					
					int x = e.getX();
					int y = e.getY();
					
					if(x >= button.getX() && x <= button.getX()+button.getWidth() && y >= button.getY() && y <= button.getY()+button.getHeight()) {
						button.setIcon(edit_active);
						button.setCursor(new Cursor(Cursor.HAND_CURSOR));
					} else {
						button.setIcon(edit);
						button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				}
			});
			cover.setBounds(0, 0, 200, 200);
			pane.add(cover, 1);
			this.add(pane, BorderLayout.NORTH);
		} else {
			this.add(cover, BorderLayout.NORTH);			
		}
	
		JPanel info = new JPanel();
		info.setLayout(new BorderLayout());
		info.setOpaque(false);
		info.add(albumName, BorderLayout.NORTH);
		info.add(albumArtist, BorderLayout.CENTER);
		
		this.add(info, BorderLayout.CENTER);
	}
	
	private void edit(Album album) {
		if(!editDialog.getFrame().isVisible())
			editDialog.show(album);
	}
	
	public String addLinebreaks(String input, int maxLineLength) {
	    StringTokenizer tok = new StringTokenizer(input, " ");
	    StringBuilder output = new StringBuilder(input.length());
	    int lineLen = 0;
	    while (tok.hasMoreTokens()) {
	        String word = tok.nextToken()+" ";

	        if (lineLen + word.length() > maxLineLength) {
	            output.append("\n");
	            lineLen = 0;
	        }
	        output.append(word);
	        lineLen += word.length();
	    }
	    return output.toString();
	}
	
	public String shortenText(String input, int maxLength) {
		String output = null;
		if(input.length() > maxLength) {
			output = input.substring(0, maxLength-3) + "...";
		} else {
			output = input;
		}
		
	    return output;
	}
}
