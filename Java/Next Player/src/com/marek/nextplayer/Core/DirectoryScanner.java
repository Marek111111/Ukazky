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

public class DirectoryScanner extends Thread {
	
	private final FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3", "mp3");
	private LinkedList<File> files = new LinkedList<File>();
	
	private String directory;
	
	public DirectoryScanner(String directory) {
		this.directory = directory;
	}
	
	public void run() {
	    scanDirectory(directory);
	    
	    try {
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void scanDirectory(String directory) {
		File dir = new File(directory);
	    File[] firstLevelFiles = dir.listFiles();
	    if (firstLevelFiles != null && firstLevelFiles.length > 0) {
	        for (File aFile : firstLevelFiles) {
	            if (aFile.isDirectory()) {
	            	scanDirectory(aFile.getAbsolutePath());
	            } else {
	            	if(filter.accept(aFile)) {
	            		addFileToFiles(aFile);
	            	}
	            }
	        }
	    }
	}
	
	private void addFileToFiles(File file) {
		if(!files.contains(file)) {
			files.add(file);
			System.out.println("Song file <" + file.getName() + "> added.");
		}
	}
	
	public LinkedList<File> getFiles() {
		return files;
	}

}
