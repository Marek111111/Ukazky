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

import java.io.File;
import java.util.LinkedList;

import javax.swing.filechooser.FileNameExtensionFilter;

public class FolderScanner extends Thread {
	
	private LinkedList<File> musicFiles = new LinkedList<File>();
	private final FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3", "mp3");
	
	public File getMusicFilesFromDirectory(String workingPath) {
	    File dir = new File(workingPath);
	    File[] firstLevelFiles = dir.listFiles();
	    if (firstLevelFiles != null && firstLevelFiles.length > 0) {
	        for (File aFile : firstLevelFiles) {
	            if (aFile.isDirectory()) {
	            	getMusicFilesFromDirectory(aFile.getAbsolutePath());
	            } else {
	            	if(filter.accept(aFile)) {
	            		//musicFiles.add(aFile);
	            		System.out.println("Scanned song file <" + aFile.getName() + ">");
	            		
	            		return aFile;
	            	}
	            }
	        }
	    }
	    
	    return null;
	}
	
	public boolean folderIsEmpty() {
		return musicFiles.isEmpty();
	}
	
	public LinkedList<File> getMusicFiles() {
		return musicFiles;
	}
	
	public void clearMusicFiles() {
		musicFiles.clear();
	}

}
