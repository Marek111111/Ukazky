

package com.marek.nextplayer.Core;

import java.io.File;
import java.util.ArrayList;

public class FileChecker extends Thread implements Runnable {
	
	private static String status = "OK";
	
	private static final ArrayList<File> fileList = new ArrayList<File>() {{
		// DATA
		add(new File("Data/Fonts/SourceSansPro-Bold.ttf"));
		add(new File("Data/Fonts/SourceSansPro-Light.ttf"));
		add(new File("Data/Fonts/SourceSansPro-Regular.ttf"));
		add(new File("Data/Fonts/SourceSansPro-Semibold.ttf"));
		
		// IMAGES
		add(new File("Images/close.png"));
		add(new File("Images/minimize.png"));
		add(new File("Images/next_active.png"));
		add(new File("Images/next_widget_active.png"));
		add(new File("Images/next_widget.png"));
		add(new File("Images/next.png"));
		add(new File("Images/no-cover.png"));
		add(new File("Images/normalMode.png"));
		add(new File("Images/pause_active.png"));
		add(new File("Images/pause_widget_active.png"));
		add(new File("Images/pause_widget.png"));
		add(new File("Images/pause.png"));
		add(new File("Images/play_active.png"));
		add(new File("Images/play_widget_active.png"));
		add(new File("Images/play_widget.png"));
		add(new File("Images/play-indicator-empty.png"));
		add(new File("Images/play-indicator.gif"));
		add(new File("Images/play-indicator.png"));
		add(new File("Images/play.png"));
		add(new File("Images/previous_active.png"));
		add(new File("Images/previous_widget_active.png"));
		add(new File("Images/previous_widget.png"));
		add(new File("Images/previous.png"));
		add(new File("Images/settings_active.png"));
		add(new File("Images/settings.png"));
		add(new File("Images/shuffle_active.png"));
		add(new File("Images/shuffle.png"));
		add(new File("Images/simpleMode_active.png"));
		add(new File("Images/simpleMode.png"));
		add(new File("Images/switcherOff.png"));
		add(new File("Images/switcherOn.png"));
		add(new File("Images/volume_active.png"));
		add(new File("Images/volume.png"));
		add(new File("Images/widgetMode.png"));
		
		// LIBRARIES
		add(new File("Libraries/gson-2.2.4.jar"));
		add(new File("Libraries/jl1.0.1.jar"));
		add(new File("Libraries/mp3agic.jar"));
		add(new File("Libraries/mp3spi1.9.5.jar"));
		add(new File("Libraries/tritonus_share.jar"));
		
		// ICON
		add(new File("Icon.png"));
	}};
	
	@Override
	public void run() {
		super.run();
		
		checkFiles();
		try {
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void checkFiles() {
		for(File f : fileList) {
			if(Variables.DEBUG)
				System.out.println("Checking file: " + f.getName());
			
			if(!f.exists())
				status = f.getAbsolutePath();
		}
	}
	
	public String getStatus() {
		return status;
	}

}
