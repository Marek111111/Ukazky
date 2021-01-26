

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.marek.nextplayer.Core.Album;
import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Fonts;
import com.marek.nextplayer.Core.Song;
import com.marek.nextplayer.Core.Utils;

public class AlbumSlotCustom extends JPanel {
	
	private Core core;
	
	private Image image;
	
	public AlbumSlotCustom(final Core core) {
		this.core = core;
		
		try {
			this.image = ImageIO.read(new File("Images/add-playlist.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 280));
		this.setOpaque(false);
		
		addComponents();
	}

	private void addComponents() {
		final JLabel icon = new JLabel(new ImageIcon("Images/add-playlist.png"));
		icon.setPreferredSize(new Dimension(200, 200));
		icon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		icon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getEditAlbumDialog().show();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				icon.setIcon(new ImageIcon(image.getScaledInstance(190, 190, Image.SCALE_SMOOTH)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				icon.setIcon(new ImageIcon(image));
			}
		});
		this.add(icon, BorderLayout.CENTER);
	}

}

/*class NewAlbumDialog {
	
	private Core core;
	private JFrame frame;
	
	private BufferedImage albumCover;
	private Image image;
	
	private ImageIcon coverIcon;
	
	private JFileChooser chooser = new JFileChooser();
	
	private JPanel container;
	private JLabel cover;
	private JTextField nameField;
	private JTextField artistField;
	
	private LinkedList<Song> songsToAdd = new LinkedList<Song>();
	
	private Point initialClick;
	
	public NewAlbumDialog(Core core) {
		this.core = core;
		
		frame = new JFrame();
		frame.setTitle("Next Player - Create New Album");
		frame.setSize(350, 600);
		frame.setIconImage(new ImageIcon("Icon.png").getImage());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.getRootPane().setBorder(new LineBorder(GUI.DARK));
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
        
        addComponents();
        
        frame.setVisible(true);
	}
	
	public void show() {
		frame = new JFrame();
		frame.setTitle("Next Player - Create New Album");
		frame.setSize(350, 600);
		frame.setIconImage(new ImageIcon("Icon.png").getImage());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.getRootPane().setBorder(new LineBorder(GUI.DARK));
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
        
        addComponents();
        
        frame.setVisible(true);
	}
	
	private void addComponents() {
		frame.setLayout(new BorderLayout());
		frame.add(new Controls(core, this), BorderLayout.NORTH);
		
		JScrollPane pane = new JScrollPane();
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.getVerticalScrollBar().setUI(new CustomScrollBar(false));
		pane.getVerticalScrollBar().setBackground(GUI.LIGHT);
		pane.getVerticalScrollBar().setPreferredSize(new Dimension(3, 10));
		pane.getVerticalScrollBar().setCursor(new Cursor(Cursor.HAND_CURSOR));
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.getVerticalScrollBar().setUnitIncrement(6);
		pane.setBorder(new EmptyBorder(0, 0, 0, 3));
		pane.setOpaque(false);
		
		container = new JPanel();
		container.setLayout(new WrapLayout(350));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));
		container.setBackground(GUI.LIGHT);
		
		cover = new JLabel();
		cover.setCursor(new Cursor(Cursor.HAND_CURSOR));
		try {
			this.albumCover = ImageIO.read(new File("Images/no-cover.png"));
			this.image = albumCover.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		coverIcon = new ImageIcon(image);
		cover.setIcon(coverIcon);
		cover.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					chooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
					int returnVal = chooser.showOpenDialog(cover);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						BufferedImage image = null;
						try {
							image = ImageIO.read(chooser.getSelectedFile());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						coverIcon = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
						cover.setIcon(coverIcon);
					}
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				cover.setIcon(new ImageIcon(coverIcon.getImage()){
					@Override
					public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
						super.paintIcon(c, g, x, y);
						new ImageIcon("Images/album-edit_overlay.png").paintIcon(c, g, x, y);
					}
				});
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				cover.setIcon(coverIcon);
			}
		});
		container.add(cover);
		
		container.add(createEmptyLabel());
		
		nameField = createTextField("Album Name");
		nameField.setHorizontalAlignment(JTextField.CENTER);
		container.add(nameField);
		
		artistField = createTextField("Album Artist");
		artistField.setHorizontalAlignment(JTextField.CENTER);
		container.add(artistField);
		
		container.add(createEmptyLabel());
		
		// SPACE FOR SONGS
		
		container.add(createEmptyLabel());
		
		final JLabel addSong = new JLabel("Add Song", JLabel.CENTER);
		addSong.setPreferredSize(new Dimension(100, 50));
		addSong.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addSong.setFont(Fonts.regular(16));
		addSong.setForeground(GUI.DARK);
		addSong.setBorder(new LineBorder(GUI.BLUE));
		addSong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					createDialog().setVisible(true);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				addSong.setBorder(new LineBorder(GUI.DARK));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				addSong.setBorder(new LineBorder(GUI.BLUE));
			}
		});
		container.add(addSong);
		
		container.add(createEmptyLabel());
		
		final JLabel save = new JLabel("Save", JLabel.CENTER);
		save.setPreferredSize(new Dimension(100, 50));
		save.setCursor(new Cursor(Cursor.HAND_CURSOR));
		save.setFont(Fonts.regular(16));
		save.setForeground(GUI.DARK);
		save.setBorder(new LineBorder(GUI.BLUE));
		save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					createNewAlbum();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				save.setBorder(new LineBorder(GUI.DARK));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				save.setBorder(new LineBorder(GUI.BLUE));
			}
		});
		container.add(save);
		
		pane.setViewportView(container);
		
		frame.add(pane, BorderLayout.CENTER);
	}
	
	private JLabel createEmptyLabel() {
		JLabel label = new JLabel(" ");
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setPreferredSize(new Dimension(300, 15));
		return label;
	}
	
	private JTextField createTextField(String value) {
		final JTextField field = new JTextField(value);
		field.setCaretPosition(0);
		field.setPreferredSize(new Dimension(300, 30));
		field.setBorder(BorderFactory.createCompoundBorder(new LineBorder(GUI.BLUE), new EmptyBorder(0, 5, 0, 5)));
		field.setFont(Fonts.regular(16));
		field.setForeground(GUI.DARK);
		field.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				field.setBorder(new LineBorder(GUI.DARK));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				field.setBorder(new LineBorder(GUI.BLUE));
			}
		});
		
		return field;
	}
	
	private JPanel createSongTree(final JDialog dialog) {
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setBackground(GUI.LIGHT);
		
		JScrollPane pane = new JScrollPane();
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.getVerticalScrollBar().setUI(new CustomScrollBar(false));
		pane.getVerticalScrollBar().setBackground(GUI.LIGHT);
		pane.getVerticalScrollBar().setPreferredSize(new Dimension(3, 10));
		pane.getVerticalScrollBar().setCursor(new Cursor(Cursor.HAND_CURSOR));
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.getVerticalScrollBar().setUnitIncrement(6);
		pane.setBorder(new EmptyBorder(0, 0, 0, 3));
		
		JPanel panel = new JPanel();
		panel.setLayout(new WrapLayout(348));
		panel.setBackground(GUI.LIGHT);
		
		for(Song s : core.getLibraryManager().getLibrary().getSongs()) {
			if(!songsToAdd.contains(s))
				panel.add(createLabel(348, 30, s.getName()));
		}
		for(final Component c : panel.getComponents()) {
			c.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						if(!songsToAdd.contains(core.getLibraryManager().getLibrary().getSong(c.getName()))) {
							addSongToNewAlbum(core.getLibraryManager().getLibrary().getSong(c.getName()));
							
							JPanel songPanel = createPanel();
							songPanel.setName(c.getName());
							
							JTextField song = createTextField(c.getName());
							song.setName(c.getName());
							song.setHorizontalAlignment(JTextField.CENTER);
							songPanel.add(song, BorderLayout.CENTER);
							
							NewAlbumDialog.this.container.add(songPanel, NewAlbumDialog.this.container.getComponentCount() - 4);
							NewAlbumDialog.this.container.revalidate();
							
							dialog.setVisible(false);
						}
					}
				};
			});
		}
		
		pane.setViewportView(panel);
		pane.setOpaque(false);
		container.add(pane, BorderLayout.CENTER);
		
		final JLabel close = new JLabel("Close", JLabel.CENTER);
		close.setPreferredSize(new Dimension(100, 50));
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.setFont(Fonts.regular(16));
		close.setForeground(GUI.DARK);
		close.setBorder(new LineBorder(GUI.BLUE));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					dialog.setVisible(false);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				close.setBorder(new LineBorder(GUI.DARK));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				close.setBorder(new LineBorder(GUI.BLUE));
			}
		});
		container.add(close, BorderLayout.SOUTH);
		
		return container;
	}
	
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 30));
		panel.setOpaque(false);
		return panel;
	}
	
	private JLabel createLabel(int width, int height, String text) {
		final JLabel label = new JLabel(text, JLabel.CENTER);
		label.setName(text);
		label.setPreferredSize(new Dimension(width, height));
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.setFont(Fonts.regular(16));
		label.setForeground(GUI.DARK);
		label.setBackground(GUI.LIGHT);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				label.setBackground(new Color(205, 240, 255));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				label.setBackground(GUI.LIGHT);
			}
		});
		label.setOpaque(true);
		return label;
	}
	
	private JDialog createDialog() {
		JDialog dialog = new JDialog();
		dialog.setSize(348, 518);
		dialog.setLocationRelativeTo(null);
		dialog.setUndecorated(true);
		dialog.add(createSongTree(dialog));
		
		return dialog;
	}
	
	private void addSongToNewAlbum(Song song) {
		songsToAdd.add(song);
	}
	
	private void createNewAlbum() {
		Album newAlbum = new Album(nameField.getText(), artistField.getText(), Utils.imageToAttachedPicture(chooser.getSelectedFile()));
		for(Song s : songsToAdd) {
			core.getLibraryManager().changeSongAlbum(s, core.getLibraryManager().getLibrary().getAlbum(s.getAlbum()), newAlbum);
		}
		core.getLibraryManager().saveAlbum(newAlbum);
		
		core.refreshMusicLibrary();
		close();
	}
	
	public void minimize() {
		frame.setState(JFrame.ICONIFIED);
	}
	
	public void close() {
		songsToAdd.clear();
		frame.dispose();
	}
	
}*/
