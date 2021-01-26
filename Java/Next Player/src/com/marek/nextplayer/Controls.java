

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Fonts;
import com.marek.nextplayer.Core.Utils;
import com.marek.nextplayer.Core.Variables;

public class Controls extends JPanel {
	
	private Core core;
	
	private Settings settings;
	private EditAlbumDialog editAlbumDialog;
	
	private Color activeColor;
	private Point initialClick;
	
	public Controls(Core core, final boolean inWidget, final boolean inSettings, final boolean inEdit) {
		this.core = core;
		
		if(inWidget) {
			assemblyForWidget();
		} else if(inSettings) {
			assemblyForSettings();
		} else if(inEdit) {
			assemblyForEdit();
		} else {
			assemblyForMainFrame(); 
		}
	}
	
	public Controls(Core core, EditAlbumDialog editAlbumDialog) {
		this.core = core;
		this.editAlbumDialog = editAlbumDialog;
		
		assemblyForNewAlbum();
	}
	
	private void assemblyForMainFrame() {
		this.setLayout(new BorderLayout());
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
        
        activeColor = GUI.BLUE;
        
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        settingsPanel.setOpaque(false);
        
        final ImageIcon settingsIcon = new ImageIcon("Images/settings.png");
        final ImageIcon settingsIcon_active = new ImageIcon("Images/settings_active.png");
        final JLabel settings = new JLabel(settingsIcon);
    	settings.setBorder(new EmptyBorder(0, 0, 0, 7));
		settings.setToolTipText("Settings");
		settings.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getSettings().show();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				settings.setIcon(settingsIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				settings.setIcon(settingsIcon);
			}
		});
		settingsPanel.add(settings, BorderLayout.WEST);
		
		if(Variables.get(Variables.SIMPLE_MODE_BUTTON_SHOW).isTrue()) {
			final ImageIcon simpleModeIcon = new ImageIcon("Images/simpleMode.png");
			final ImageIcon simpleModeIcon_active = new ImageIcon("Images/simpleMode_active.png");
			final JLabel simpleMode = new JLabel(simpleModeIcon);
			simpleMode.setToolTipText("Simple Mode");
			simpleMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
			simpleMode.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);
					
					if(e.getButton() == MouseEvent.BUTTON1) {
						core.simpleMode();
						
						if(Variables.get(Variables.SIMPLE_MODE).isTrue()) {
							simpleMode.setIcon(simpleModeIcon);
							simpleMode.setToolTipText("Normal Mode");
						} else {
							simpleMode.setIcon(simpleModeIcon);
							simpleMode.setToolTipText("Simple Mode");
					}
					}
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);
					
					if(Variables.get(Variables.SIMPLE_MODE).isTrue()) {
						simpleMode.setIcon(simpleModeIcon_active);
					} else {
						simpleMode.setIcon(simpleModeIcon_active);
					}
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					
					if(Variables.get(Variables.SIMPLE_MODE).isTrue()) {
						simpleMode.setIcon(simpleModeIcon);
					} else {
						simpleMode.setIcon(simpleModeIcon);
					}
				}
			});
			settingsPanel.add(simpleMode, BorderLayout.EAST);
		}
		
		this.add(settingsPanel, BorderLayout.WEST);
		
		JPanel minWidClose = new JPanel();
		minWidClose.setLayout(new BorderLayout());
		minWidClose.setBorder(new EmptyBorder(10, 10, 10, 10));
		minWidClose.setOpaque(false);
		
		final ImageIcon minimizeIcon = new ImageIcon("Images/minimize.png");
		final ImageIcon minimizeIcon_active = new ImageIcon("Images/minimize_active.png");
		final JLabel min = new JLabel(minimizeIcon);
		min.setBorder(new EmptyBorder(0, 0, 0, 15));
		min.setToolTipText("Minimize");
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getMainFrame().minimize();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				min.setIcon(minimizeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				min.setIcon(minimizeIcon);
			}
		});
		minWidClose.add(min, BorderLayout.WEST);
		
		if(Variables.get(Variables.WIDGET_MODE_BUTTON_SHOW).isTrue()) {
			final ImageIcon widgetModeIcon = new ImageIcon("Images/widgetMode.png");
			final ImageIcon widgetModeIcon_active = new ImageIcon("Images/widgetMode_active.png");
			final JLabel widgetMode = new JLabel(widgetModeIcon);
			widgetMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
			widgetMode.setToolTipText("Widget Mode");
			widgetMode.setBorder(new EmptyBorder(0, 0, 0, 15));
			widgetMode.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);
					
					if(e.getButton() == MouseEvent.BUTTON1)
						core.widgetMode();
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					super.mouseEntered(e);
					
					widgetMode.setIcon(widgetModeIcon_active);
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					super.mouseExited(e);
					
					widgetMode.setIcon(widgetModeIcon);
				}
			});
			minWidClose.add(widgetMode, BorderLayout.CENTER);
		}
		
		final ImageIcon closeIcon = new ImageIcon("Images/close.png");
		final ImageIcon closeIcon_active = new ImageIcon("Images/close_active.png");
		final JLabel close = new JLabel(closeIcon);
		close.setToolTipText("Close");
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.close();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				close.setIcon(closeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				close.setIcon(closeIcon);
			}
		});
		minWidClose.add(close, BorderLayout.EAST);
		
		this.add(minWidClose, BorderLayout.EAST);
	}
	
	private void assemblyForWidget() {
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		activeColor = new Color(81, 81, 200);
        
		JPanel minWidClose = new JPanel();
		minWidClose.setLayout(new BorderLayout());
		minWidClose.setBorder(new EmptyBorder(5, 5, 5, 5));
		minWidClose.setOpaque(false);
		
		final ImageIcon minimizeIcon = new ImageIcon("Images/minimize.png");
		final ImageIcon minimizeIcon_active = new ImageIcon("Images/minimize_active.png");
		final JLabel min = new JLabel(minimizeIcon);
		min.setBorder(new EmptyBorder(0, 0, 0, 10));
		min.setToolTipText("Minimize");
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getWidget().minimize();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				min.setIcon(minimizeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				min.setIcon(minimizeIcon);
			}
		});
		minWidClose.add(min, BorderLayout.WEST);
		
		final ImageIcon widgetModeIcon = new ImageIcon("Images/widgetMode.png");
		final ImageIcon widgetModeIcon_active = new ImageIcon("Images/widgetMode_active.png");
		final JLabel widgetMode = new JLabel(widgetModeIcon);
		widgetMode.setCursor(new Cursor(Cursor.HAND_CURSOR));
		widgetMode.setToolTipText("Normal Mode");
		widgetMode.setBorder(new EmptyBorder(0, 0, 0, 10));
		widgetMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.widgetMode();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				widgetMode.setIcon(widgetModeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				widgetMode.setIcon(widgetModeIcon);
			}
		});
		minWidClose.add(widgetMode, BorderLayout.CENTER);
		
		final ImageIcon closeIcon = new ImageIcon("Images/close.png");
		final ImageIcon closeIcon_active = new ImageIcon("Images/close_active.png");
		final JLabel close = new JLabel(closeIcon);
		close.setToolTipText("Close");
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.close();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				close.setIcon(closeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				close.setIcon(closeIcon);
			}
		});
		minWidClose.add(close, BorderLayout.EAST);
		
		this.add(minWidClose, BorderLayout.EAST);
	}
	
	private void assemblyForSettings() {
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.DARK);
		
		activeColor = new Color(0, 180, 255);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        settingsPanel.setOpaque(false);
        
        final JLabel settingsLabel = new JLabel("Settings");
		settingsLabel.setForeground(GUI.LIGHT);
		settingsLabel.setFont(Fonts.semibold(16));
		settingsPanel.add(settingsLabel, BorderLayout.WEST);
        
		this.add(settingsPanel, BorderLayout.WEST);
		
		JPanel minWidClose = new JPanel();
		minWidClose.setLayout(new BorderLayout());
		minWidClose.setBorder(new EmptyBorder(10, 10, 10, 10));
		minWidClose.setOpaque(false);
		
		final ImageIcon aboutIcon = new ImageIcon("Images/about.png");
		final JLabel about = new JLabel(aboutIcon);
		about.setBorder(new EmptyBorder(0, 0, 0, 15));
		about.setToolTipText("About");
		about.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		final About aboutFrame = new About();
		about.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					aboutFrame.setVisible(true);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				about.setIcon(new ImageIcon("Images/about_active.png"));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				about.setIcon(aboutIcon);
			}
		});
		minWidClose.add(about, BorderLayout.WEST);
		
		final ImageIcon minIcon = new ImageIcon("Images/minimize.png");
		final ImageIcon minimizeIcon_active = new ImageIcon("Images/minimize_active.png");
		final JLabel min = new JLabel(minIcon);
		min.setBorder(new EmptyBorder(0, 0, 0, 15));
		min.setToolTipText("Minimize");
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getSettings().minimize();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				min.setIcon(minimizeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				min.setIcon(minIcon);
			}
		});
		minWidClose.add(min, BorderLayout.CENTER);
		
		final ImageIcon closeIcon = new ImageIcon("Images/close.png");
		final ImageIcon closeIcon_active = new ImageIcon("Images/close_active.png");
		final JLabel close = new JLabel(closeIcon);
		close.setToolTipText("Close");
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getSettings().hide();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				close.setIcon(closeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				close.setIcon(closeIcon);
			}
		});
		minWidClose.add(close, BorderLayout.EAST);
		
		this.add(minWidClose, BorderLayout.EAST);
	}
	
	private void assemblyForEdit() {
		this.editAlbumDialog = core.getEditAlbumDialog();
		
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.DARK);
		
		activeColor = new Color(0, 180, 255);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        settingsPanel.setOpaque(false);
        
        final JLabel settings = new JLabel("Edit Album");
		settings.setForeground(GUI.LIGHT);
		settings.setFont(Fonts.semibold(16));
		settingsPanel.add(settings, BorderLayout.WEST);
        
		this.add(settingsPanel, BorderLayout.WEST);
		
		JPanel minWidClose = new JPanel();
		minWidClose.setLayout(new BorderLayout());
		minWidClose.setBorder(new EmptyBorder(10, 10, 10, 10));
		minWidClose.setOpaque(false);
		
		final ImageIcon minIcon = new ImageIcon("Images/minimize.png");
		final ImageIcon minimizeIcon_active = new ImageIcon("Images/minimize_active.png");
		final JLabel min = new JLabel(minIcon);
		min.setBorder(new EmptyBorder(0, 0, 0, 15));
		min.setToolTipText("Minimize");
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					editAlbumDialog.minimize();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				min.setIcon(minimizeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				min.setIcon(minIcon);
			}
		});
		minWidClose.add(min, BorderLayout.CENTER);
		
		final ImageIcon closeIcon = new ImageIcon("Images/close.png");
		final ImageIcon closeIcon_active = new ImageIcon("Images/close_active.png");
		final JLabel close = new JLabel(closeIcon);
		close.setToolTipText("Close");
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					editAlbumDialog.close();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				close.setIcon(closeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				close.setIcon(closeIcon);
			}
		});
		minWidClose.add(close, BorderLayout.EAST);
		
		this.add(minWidClose, BorderLayout.EAST);
	}
	
	private void assemblyForNewAlbum() {
		this.setLayout(new BorderLayout());
		this.setBackground(GUI.DARK);
		
		activeColor = new Color(0, 180, 255);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        settingsPanel.setOpaque(false);
        
        final JLabel settings = new JLabel("Create New Album");
		settings.setForeground(GUI.LIGHT);
		settings.setFont(Fonts.semibold(16));
		settingsPanel.add(settings, BorderLayout.WEST);
        
		this.add(settingsPanel, BorderLayout.WEST);
		
		JPanel minWidClose = new JPanel();
		minWidClose.setLayout(new BorderLayout());
		minWidClose.setBorder(new EmptyBorder(10, 10, 10, 10));
		minWidClose.setOpaque(false);
		
		final ImageIcon minIcon = new ImageIcon("Images/minimize.png");
		final ImageIcon minimizeIcon_active = new ImageIcon("Images/minimize_active.png");
		final JLabel min = new JLabel(minIcon);
		min.setBorder(new EmptyBorder(0, 0, 0, 15));
		min.setToolTipText("Minimize");
		min.setCursor(new Cursor(Cursor.HAND_CURSOR));
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					editAlbumDialog.minimize();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				min.setIcon(minimizeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				min.setIcon(minIcon);
			}
		});
		minWidClose.add(min, BorderLayout.CENTER);
		
		final ImageIcon closeIcon = new ImageIcon("Images/close.png");
		final ImageIcon closeIcon_active = new ImageIcon("Images/close_active.png");
		final JLabel close = new JLabel(closeIcon);
		close.setToolTipText("Close");
		close.setCursor(new Cursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					editAlbumDialog.close();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				close.setIcon(closeIcon_active);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				close.setIcon(closeIcon);
			}
		});
		minWidClose.add(close, BorderLayout.EAST);
		
		this.add(minWidClose, BorderLayout.EAST);
	}

}
