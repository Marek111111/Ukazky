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

package com.marek.nextplayer.Core;

import java.util.LinkedList;


public class Variables extends Thread {
	
	public static boolean DEBUG = true;
	
	public static final String MUSIC_PATH = "MUSIC_PATH";
	
	public static final String SIMPLE_MODE = "SIMPLE_MODE";
	public static final String SIMPLE_MODE_BUTTON_SHOW = "SIMPLE_MODE_BUTTON_SHOW";
	public static final String SIMPLE_MODE_FULL = "SIMPLE_MODE_FULL";
	public static final String SIMPLE_MODE_ALWAYS_ON_TOP = "SIMPLE_MODE_ALWAYS_ON_TOP";
	
	public static final String WIDGET_MODE = "WIDGET_MODE";
	public static final String WIDGET_MODE_BUTTON_SHOW = "WIDGET_MODE_BUTTON_SHOW";
	public static final String WIDGET_MODE_ALWAYS_ON_TOP = "WIDGET_MODE_ALWAYS_ON_TOP";
	
	private static LinkedList<Variable> variableList = new LinkedList<Variable>() {
		{
			add(new Variable(MUSIC_PATH, "null"));
			
			add(new Variable(SIMPLE_MODE, "false"));
			add(new Variable(SIMPLE_MODE_BUTTON_SHOW, "false"));
			add(new Variable(SIMPLE_MODE_FULL, "true"));
			add(new Variable(SIMPLE_MODE_ALWAYS_ON_TOP, "true"));
			
			add(new Variable(WIDGET_MODE, "false"));
			add(new Variable(WIDGET_MODE_BUTTON_SHOW, "false"));
			add(new Variable(WIDGET_MODE_ALWAYS_ON_TOP, "true"));
		}
	};
	
	public void run() {
		load();
		
		try {
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void load() {
		for(Variable var : variableList) {
			String value = Properties.read(var.getName());
			
			if(value == null) {
				set(var, var.getValue());
			} else {
				set(var, value);
			}
			
			System.out.println("Name: " + var.getName() + ", Value: " + var.getValue());
		}
	}
	
	public static void set(Variable variable, String value) {
		variable.set(value);
		Properties.write(variable.getName(), value);
	}
	
	public static Variable get(String variableName) {
		for(Variable var : variableList) {
			if(var.getName() == variableName)
				return var;
		}
		
		return null;
	}

}
