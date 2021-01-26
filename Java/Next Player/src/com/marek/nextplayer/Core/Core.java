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

import javax.swing.SwingUtilities;

import com.marek.nextplayer.EditAlbumDialog;
import com.marek.nextplayer.ErrorDialog;
import com.marek.nextplayer.MainFrame;
import com.marek.nextplayer.MusicDirDialog;
import com.marek.nextplayer.Settings;
import com.marek.nextplayer.WaitDialog;
import com.marek.nextplayer.Widget;

public class Core extends Thread implements Runnable {
	
	private MusicDirDialog musicDirDialog;
	private WaitDialog waitDialog;
	
	private DirectoryScanner directoryScanner;
	private LibraryManager libraryManager;
	
	private MainFrame mainFrame;
	private Settings settings;
	private EditAlbumDialog editAlbumDialog;
	private Widget widget;
	
	private Player player;
	
	public Core() {
		musicDirDialog = new MusicDirDialog(this);
		waitDialog = new WaitDialog();
		libraryManager = new LibraryManager(this);
		
		settings = new Settings(this);
		editAlbumDialog = new EditAlbumDialog(this);
		widget = new Widget(this);
		
		player = new Player(this);
	}
	
	@Override
	public void run() {
		super.run();
		
		System.out.println("Core initialized!");
		init();
	}
	
	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				waitDialog.show("Loading your music...");
			}
		});
		
		checkFiles();
	}
	
	private void checkFiles() {
		FileChecker fileChecker = new FileChecker();
		fileChecker.run();
		
		if(fileChecker.getStatus() != "OK") {
			new ErrorDialog("File " + fileChecker.getStatus() + " is missing, please reinstall the program.");
			waitDialog.hide();
			stopCore();
		} else {
			System.out.println("##### Files checked!");
			loadVariables();
		}
	}
	
	private void loadVariables() {
		new Thread(new Variables()).run();
		System.out.println("##### Variables loaded!");

		scanDirectory();
	}
	
	private void scanDirectory() {
		directoryScanner = new DirectoryScanner(Variables.get(Variables.MUSIC_PATH).getValue());
		new Thread(directoryScanner).run();
		
		if(directoryScanner.getFiles().isEmpty()) {
			musicDirDialog.show();
			waitDialog.hide();
		} else {
			waitDialog.show("Loading your music...");
			musicDirDialog.hide();
			System.out.println("##### Directory scan finished!");

			createLibrary();
		}
	}
	
	private void createLibrary() {
		if(Variables.get(Variables.MUSIC_PATH).getValue().equals("null")) {
			musicDirDialog.show();
			waitDialog.hide();
		} else {
			new Thread(libraryManager).run();			
			System.out.println("##### Music library has been created!");
			
			if(mainFrame == null) {
				createMainFrame();
			} else {
				mainFrame.getAlbumView().refreshAlbumView();
				mainFrame.getSideBar().refresh();
				waitDialog.hide();
				System.out.println("##### Music Library has been refreshed!");
			}
		}
	}
	
	private void createMainFrame() {
		final Core core = this;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				mainFrame = new MainFrame(core);
				waitDialog.hide();
			}
		});
		
		System.out.println("##### Main Frame has been created!");
	}
	
	public void refreshMusicLibrary() {
		directoryScanner.getFiles().clear();
		libraryManager.clearLibrary();
		scanDirectory();
	}

	public void stopCore() {
		this.interrupt();
	}
	
	public void simpleMode() {
		if(Variables.get(Variables.SIMPLE_MODE).isTrue()) {
			mainFrame.simpleMode(false);
			Variables.get(Variables.SIMPLE_MODE).set("false");
		} else {
			mainFrame.simpleMode(true);
			Variables.get(Variables.SIMPLE_MODE).set("true");						
		}
	}
	
	public void widgetMode() {
		if(Variables.get(Variables.WIDGET_MODE).isTrue()) {
			widget.close();
			mainFrame.getFrame().setVisible(true);
			Variables.get(Variables.WIDGET_MODE).set("false");
		} else {
			if(player.getSong() == null) {
				widget.show(mainFrame.getSideBar().getPlayList().getActivePlaylist().get(0));
			} else {
				widget.show(player.getSong());
			}
			mainFrame.getFrame().setVisible(false);
			Variables.get(Variables.WIDGET_MODE).set("true");
		}
	}
	
	public void close() {
		System.exit(0);
	}
	
	public MusicDirDialog getMusicDirDialog() {
		return musicDirDialog;
	}
	
	public WaitDialog getWaitDialog() {
		return waitDialog;
	}
	
	public DirectoryScanner getDirectoryScanner() {
		return directoryScanner;
	}
	
	public LibraryManager getLibraryManager() {
		return libraryManager;
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public Widget getWidget() {
		return widget;
	}
	
	public EditAlbumDialog getEditAlbumDialog() {
		return editAlbumDialog;
	}
	
	public Player getPlayer() {
		return player;
	}

}
