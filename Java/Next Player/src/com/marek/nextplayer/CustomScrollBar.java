/*	  Next Player - The next music player
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
