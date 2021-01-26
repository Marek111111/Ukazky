

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
