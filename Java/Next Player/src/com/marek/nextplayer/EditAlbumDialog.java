

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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

public class EditAlbumDialog {
	
	private Core core;
	
	private JFrame frame;
	
	private Album album;
	
	private BufferedImage albumCover;
	private Image image;
	
	private JLabel cover;
	private JFileChooser chooser = new JFileChooser();
	private ImageIcon coverIcon;
	private JTextField nameField;
	private JTextField artistField;
	private LinkedList<JTextField> songFields = new LinkedList<JTextField>();
	
	private JPanel container;
	private LinkedList<JPanel> songPanels = new LinkedList<JPanel>();
	private LinkedList<Song> songsToDelete = new LinkedList<Song>();
	private LinkedHashMap<Song, String> changeSongsAlbum = new LinkedHashMap<Song, String>();
	private LinkedList<Song> songsToAdd = new LinkedList<Song>();
	
	private PopUpDialog popup = new PopUpDialog();
	
	private Point initialClick;
	
	public EditAlbumDialog(Core core) {
		this.core = core;
		this.frame = new JFrame();
	}
	
	public void show(Album album) {
		songPanels.clear();
		songsToDelete.clear();
		changeSongsAlbum.clear();
		songsToAdd.clear();
		songFields.clear();
		
		this.album = album;
		
		this.frame = new JFrame();
		frame.setTitle("Next Player - Edit Album");
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
        
        addComponentsNewAlbum();
        
        frame.setVisible(true);
	}
	
	private void addComponents() {
		frame.setLayout(new BorderLayout());
		frame.add(new Controls(core, false, false, true), BorderLayout.NORTH);
		
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
		
		if(album.getCoverPath() != null) {
			try {
				this.albumCover = ImageIO.read(new File(album.getCoverPath()));
				this.image = albumCover.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			} catch (IOException e) {
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
		
		nameField = createTextField(album.getName());
		nameField.setHorizontalAlignment(JTextField.CENTER);
		container.add(nameField);
		
		artistField = createTextField(album.getArtist());
		artistField.setHorizontalAlignment(JTextField.CENTER);
		container.add(artistField);
		
		container.add(createEmptyLabel());
		
		for(int i = 0; i <= album.getSongs().size()-1; i ++) {
			JPanel songPanel = createPanel();
			songPanel.setName(album.getSongs().get(i).getName());
			JTextField song = createTextField(album.getSongs().get(i).getName());
			song.setName(album.getSongs().get(i).getName());
			song.setHorizontalAlignment(JTextField.CENTER);
			songFields.add(song);
			songPanel.add(song, BorderLayout.CENTER);
			
			JPanel propPanel = new JPanel();
			propPanel.setLayout(new BorderLayout());
			propPanel.setOpaque(false);
			final ImageIcon propIcon = new ImageIcon("Images/edit-small.png");
			final ImageIcon propIcon_active = new ImageIcon("Images/edit-small_active.png");
			final JLabel prop = new JLabel(propIcon);
			prop.setCursor(new Cursor(Cursor.HAND_CURSOR));
			prop.setBorder(new EmptyBorder(0, 10, 0, 0));
			prop.setName(album.getSongs().get(i).getName());
			
			final EditAlbumDialog editAlbum = this;
			prop.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);
					
					if(e.getButton() == MouseEvent.BUTTON1)
						popup.show(editAlbum, prop, album);
				};
				
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);
					
					prop.setIcon(propIcon_active);
				};
				
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					
					prop.setIcon(propIcon);
				};
			});
			propPanel.add(prop, BorderLayout.WEST);
			
			songPanel.add(propPanel, BorderLayout.EAST);
			songPanels.add(songPanel);
			container.add(songPanel);
		}
		
		/*container.add(createEmptyLabel());
		
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
		container.add(addSong);*/
		
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
					updateAlbumInfo();
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
	
	private void addComponentsNewAlbum() {
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
	
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 30));
		panel.setOpaque(false);
		return panel;
	}
	
	private JLabel createEmptyLabel() {
		JLabel label = new JLabel(" ");
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setPreferredSize(new Dimension(300, 15));
		return label;
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
							if(album != null)
								changeSongAlbum(core.getLibraryManager().getLibrary().getSong(c.getName()), album.getName());
							
							JPanel songPanel = createPanel();
							songPanel.setName(c.getName());
							
							JTextField song = createTextField(c.getName());
							song.setName(c.getName());
							song.setHorizontalAlignment(JTextField.CENTER);
							songPanel.add(song, BorderLayout.CENTER);
							
							EditAlbumDialog.this.container.add(songPanel, EditAlbumDialog.this.container.getComponentCount() - 4);
							EditAlbumDialog.this.container.revalidate();
							
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
	
	private JDialog createDialog() {
		JDialog dialog = new JDialog();
		dialog.setSize(348, 518);
		dialog.setLocationRelativeTo(null);
		dialog.setUndecorated(true);
		dialog.add(createSongTree(dialog));
		
		return dialog;
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
	
	public void addSongToDelete(Song song) {
		songsToDelete.add(song);
	}
	
	private void addSongToNewAlbum(Song song) {
		songsToAdd.add(song);
	}
	
	public void changeSongAlbum(Song song, String newAlbum) {
		changeSongsAlbum.put(song, newAlbum);
	}
	
	public void removeSongPanel(String name) {
		for(JTextField field : songFields) {
			if(field.getName().equals(name))
				songFields.remove(field);
		}
		for(JPanel p : songPanels) {
			if(p.getName().equals(name)) {
				container.remove(p);
				container.revalidate();
			}
		}
	}
	
	private void createNewAlbum() {
		Album newAlbum = null;
		if(chooser.getSelectedFile() != null) {
			newAlbum = new Album(nameField.getText(), artistField.getText(), chooser.getSelectedFile().getAbsolutePath());
		} else {
			newAlbum = new Album(nameField.getText(), artistField.getText(), null);
		}
		for(Song s : songsToAdd) {
			core.getLibraryManager().changeSongAlbum(s, newAlbum);
		}
		core.getLibraryManager().addAlbumToLibrary(newAlbum);
		
		core.getLibraryManager().saveLibrary();
		core.refreshMusicLibrary();
		close();
	}
	
	private void updateAlbumInfo() {
		core.getPlayer().close();
		
		for(Song s : songsToDelete) {
			core.getLibraryManager().removeSongFromAlbum(s, album);
		}
		
		for(JTextField field : songFields) {
			album.getSongByName(field.getName()).setName(field.getText());
		}
		
		for(Song song : changeSongsAlbum.keySet()) {
			core.getLibraryManager().changeSongAlbum(song, core.getLibraryManager().getLibrary().getAlbum(changeSongsAlbum.get(song)));
			System.out.println("Setting album <" + changeSongsAlbum.get(song) + "> to <" + song.getName() + ">");
		}

		album.setName(nameField.getText());
		album.setArtist(artistField.getText());
		if(chooser.getSelectedFile() != null)
			album.setCoverPath(chooser.getSelectedFile().getAbsolutePath());
		
		if(album.getSongs().isEmpty()) {
			core.getLibraryManager().removeAlbumFromLibrary(album);
		}
		
		core.getLibraryManager().saveLibrary();
		core.refreshMusicLibrary();
		close();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Core getCore() {
		return core;
	}
	
	public void minimize() {
		frame.setState(JFrame.ICONIFIED);
	}
	
	public void close() {
		frame.dispose();
	}

}

class PopUpDialog {
	
	private Core core;
	private EditAlbumDialog editDialog;
	
	private JPopupMenu menu;
	
	public void construct(final EditAlbumDialog editDialog, final Component c, final Album album) {
		this.core = editDialog.getCore();
		this.editDialog = editDialog;
		
		menu = new JPopupMenu();
		menu.setPreferredSize(new Dimension(200, 100));
		menu.setBorder(new LineBorder(GUI.DARK));
		menu.setOpaque(false);
		
		JPanel container = new JPanel();
		container.setLayout(new WrapLayout(200));
		container.setBackground(GUI.LIGHT);
		container.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		
		JPanel addToAlbum = createPanel();
		JLabel addTo = createLabel("Add to album...");
		final JDialog dialog = createDialog(album, album.getSongByName(c.getName()));
		addTo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					dialog.setVisible(true);
			}
		});
		addToAlbum.add(addTo);
		container.add(addToAlbum);
		
		JPanel openLocation = createPanel();
		JLabel location = createLabel("Open song location");
		location.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					try {
						Desktop.getDesktop().open(new File(new File(album.getSongByName(c.getName()).getPath()).getParent()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		openLocation.add(location);
		container.add(openLocation);
		
		JPanel removeSong = createPanel();
		final JLabel remove = createLabel("Remove song");
		remove.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					editDialog.addSongToDelete(album.getSongByName(c.getName()));
					editDialog.removeSongPanel(c.getName());
					menu.setVisible(false);
				}
			}
		});
		removeSong.add(remove);
		container.add(removeSong);
		
		menu.add(container);
	}
	
	private JPanel createPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setOpaque(false);
		return panel;
	}
	
	private JLabel createLabel(String text) {
		final JLabel label = new JLabel(text, JLabel.CENTER);
		label.setPreferredSize(new Dimension(200, 25));
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
	
	private JPanel createAlbumTree(final JDialog dialog, final Album album, final Song song) {
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
		
		for(Album a : core.getLibraryManager().getLibrary().getAlbums()) {
			if(!a.getName().equals(album.getName()))
				panel.add(createLabel(348, 30, a.getName()));
		}
		for(final Component c : panel.getComponents()) {
			c.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						editDialog.changeSongAlbum(song, c.getName());
						editDialog.removeSongPanel(song.getName());
						dialog.setVisible(false);
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
	
	private JDialog createDialog(Album album, Song song) {
		JDialog dialog = new JDialog();
		dialog.setSize(348, 518);
		dialog.setLocationRelativeTo(null);
		dialog.setUndecorated(true);
		dialog.add(createAlbumTree(dialog, album, song));
		
		return dialog;
	}
	
	public void show(EditAlbumDialog editDialog, Component c, Album album) {
		construct(editDialog, c, album);
		
		int x = (c.getX()+(c.getWidth()/2))-(200/2);
		int y = c.getY()+c.getHeight();
		menu.show(c, x, y);
	}
	
}
