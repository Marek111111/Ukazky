

package com.marek.nextplayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.marek.nextplayer.Core.Core;
import com.marek.nextplayer.Core.Fonts;
import com.marek.nextplayer.Core.Variable;
import com.marek.nextplayer.Core.Variables;

public class Settings {
	
	private Core core;
	
	private JFrame frame;
	
	private Point initialClick;
	
	private JPanel settingsPanel;
	
	private boolean settignsAdded = false;
	
	public Settings(Core core) {
		this.core = core;
		
		frame = new JFrame();
		frame.setTitle("Next Player - Settings");
		frame.setSize(380, 600);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
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
		frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(GUI.LIGHT);
		frame.getRootPane().setBorder(new LineBorder(GUI.DARK, 1));
		frame.add(new Controls(core, false, true, false), BorderLayout.NORTH);
		
		settingsPanel = new JPanel();
		settingsPanel.setLayout(new WrapLayout(367));
		settingsPanel.setBackground(GUI.LIGHT);
		
		JScrollPane pane = new JScrollPane(settingsPanel);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.getVerticalScrollBar().setUI(new CustomScrollBar(false));
		pane.getVerticalScrollBar().setBackground(GUI.LIGHT);
		pane.getVerticalScrollBar().setPreferredSize(new Dimension(3, 10));
		pane.getVerticalScrollBar().setCursor(new Cursor(Cursor.HAND_CURSOR));
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.getVerticalScrollBar().setUnitIncrement(6);
		pane.setBorder(new EmptyBorder(0, 0, 0, 3));
		pane.setOpaque(false);

		frame.add(pane, BorderLayout.CENTER);
		
		JLabel info = new JLabel("<html><center>* Some changes will be applied after you restart <br> this program.<center></html>");
		info.setFont(Fonts.regular(14));
		info.setForeground(GUI.DARK);
		info.setBorder(new EmptyBorder(10, 45, 10, 0));
		
		frame.add(info, BorderLayout.SOUTH);
	}
	
	public void show() {
		if(!settignsAdded) {
			settingsPanel.add(new SettingsItem("Show Simple Mode button:", new SettingsSwitch(core, Variables.get(Variables.SIMPLE_MODE_BUTTON_SHOW))));
			settingsPanel.add(new SettingsItem("Simple Mode window always on top:", new SettingsSwitch(core, Variables.get(Variables.SIMPLE_MODE_ALWAYS_ON_TOP))));
			settingsPanel.add(new SettingsItem("Simple Mode in full height:", new SettingsSwitch(core, Variables.get(Variables.SIMPLE_MODE_FULL))));
			settingsPanel.add(new SettingsItem("Show Widget Mode button:", new SettingsSwitch(core, Variables.get(Variables.WIDGET_MODE_BUTTON_SHOW))));
			settingsPanel.add(new SettingsItem("Widget window always on top:", new SettingsSwitch(core, Variables.get(Variables.WIDGET_MODE_ALWAYS_ON_TOP))));
			settingsPanel.add(new SettingsItem("Change music folder:", new SettingsSwitch(core, Variables.get(Variables.MUSIC_PATH))));
			settignsAdded = true;
		}
		frame.setVisible(true);
	}
		
	public void hide() {
		frame.setVisible(false);
	}
	
	public void minimize() {
		frame.setState(JFrame.ICONIFIED);
	}

}

class SettingsItem extends JPanel{
	
	private JLabel settingsName;
	
	public SettingsItem(String name, SettingsSwitch switcher) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(367, 40));
		this.setBorder(new EmptyBorder(0, 10, 0, 10));
		this.setOpaque(false);
		
		settingsName = new JLabel(name, JLabel.CENTER);
		settingsName.setFont(Fonts.regular(16));
		settingsName.setForeground(GUI.DARK);
		
		this.add(settingsName, BorderLayout.WEST);
		this.add(switcher, BorderLayout.EAST);
	}
	
	public String getName() {
		return settingsName.getText();
	}
	
}

class SettingsSwitch extends JLabel{
	
	private Core core;
	
	private ImageIcon on = new ImageIcon("Images/switcherOn.png");
	private ImageIcon off = new ImageIcon("Images/switcherOff.png");
	private ImageIcon on_active = new ImageIcon("Images/switcherOn_active.png");
	private ImageIcon off_active = new ImageIcon("Images/switcherOff_active.png");
	
	private boolean isOn;
	
	public SettingsSwitch(Core core, final Variable variable) {
		this.core = core;
		
		isOn = variable.isTrue();
		if(isOn) {
			this.setIcon(on);
		} else {
			this.setIcon(off);
		}
		
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					changeStatus();
					Variables.set(variable, String.valueOf(isOn));
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				if(isOn) {
					setIcon(on_active);
				} else {
					setIcon(off_active);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				if(isOn) {
					setIcon(on);
				} else {
					setIcon(off);
				}
			}
		});
	}
	
	public void changeStatus() {
		if(isOn) {
			setIcon(off);
			isOn = false;
		} else {
			setIcon(on);
			isOn = true;
		}	
	}
	
	public SettingsSwitch(final Core core, final File file) {
		this.setText("Change...");
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setFont(Fonts.regular(16));
		this.setForeground(GUI.BLUE);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(e.getButton() == MouseEvent.BUTTON1)
					core.getMusicDirDialog().show();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				
				setForeground(GUI.DARK);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				
				setForeground(GUI.BLUE);
			}
		});
	}
	
}

