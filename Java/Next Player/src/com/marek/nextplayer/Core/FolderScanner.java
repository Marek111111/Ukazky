

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
