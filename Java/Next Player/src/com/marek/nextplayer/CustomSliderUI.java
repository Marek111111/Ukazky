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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSliderUI extends BasicSliderUI {
	
	public CustomSliderUI(JSlider b) {
        super(b);
	}

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(10, 10);
    }
    
    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GUI.BLUE);
        g2d.drawLine(trackRect.x + (trackRect.width/2), trackRect.y, trackRect.x + (trackRect.width/2), trackRect.y + trackRect.height);
        g2d.drawLine(trackRect.x + (trackRect.width/2) + 1, trackRect.y, trackRect.x + (trackRect.width/2) + 1, trackRect.y + trackRect.height);
        
        int ballCenterY = thumbRect.y+(thumbRect.height/2);
        g2d.setColor(GUI.LIGHT);
        g2d.drawLine(trackRect.x + (trackRect.width/2), trackRect.y, trackRect.x + (trackRect.width/2), ballCenterY);
        g2d.drawLine(trackRect.x + (trackRect.width/2) + 1, trackRect.y, trackRect.x + (trackRect.width/2) + 1, ballCenterY);
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(GUI.BLUE);
        g2d.fillOval((int)thumbRect.getX()+1, (int)thumbRect.getY(), (int)thumbRect.getWidth(), (int)thumbRect.getHeight());
    }

}
