

package com.marek.nextplayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.RepaintManager;
import javax.swing.plaf.SliderUI;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class CustomScrollBar extends MetalScrollBarUI {
	
	private boolean withBorder;
	
	public CustomScrollBar(boolean withBorder) {
		this.withBorder = withBorder;
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		if(withBorder) {
			if(isThumbRollover()) {
				g.setColor(GUI.BLUE);
				g.fillRect(thumbBounds.x+(thumbBounds.width/3), thumbBounds.y, 3, thumbBounds.height);
			} else {
				g.setColor(GUI.BLUE);
				g.fillRect(thumbBounds.x+(thumbBounds.width/3), thumbBounds.y, 3, thumbBounds.height);
			}
		} else {
			if(isThumbRollover()) {
				g.setColor(GUI.BLUE);
				g.fillRect(thumbBounds.x, thumbBounds.y,  thumbBounds.width, thumbBounds.height);
			} else {
				g.setColor(GUI.BLUE);
				g.fillRect(thumbBounds.x, thumbBounds.y,  thumbBounds.width, thumbBounds.height);
			}
		}
	}
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(scrollbar.getBackground());
		g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	}
	
	@Override
	protected void setThumbBounds(int x, int y, int width, int height) {
		super.setThumbBounds(x, y, width, height);
	}
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton b = new JButton();
		b.setPreferredSize(new Dimension(0, 0));
		
		return b;
	}
	
	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton b = new JButton();
		b.setPreferredSize(new Dimension(0, 0));
		
		return b;
	}

}
