

package com.marek.nextplayer.Core;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.tritonus.share.sampled.file.TAudioFileFormat;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class LibraryManager extends Thread {
	
	private Core core;
	private Library library;
	
	private Data data;
	
	public LibraryManager(Core core) {
		this.core = core;
		library = new Library();
		data = new Data();
	}
	
	@Override
	public void run() {
		super.run();
		
		createLibrary();
		
		try {
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void createLibrary() {
		library = (Library) data.readData("Library", library);
		
		if(library == null) {
			library = new Library();
		} else {
			for(Song s : library.getSongs()) {
				if(!new File(s.getPath()).exists())
					library.getSongs().remove(s);
			}
			for(Album a : library.getAlbums()) {
				if(a.getCoverPath() != null) {
					if(!new File(a.getCoverPath()).exists()) {
						a.setCoverPath(null);
						createCoverForAlbum(a);
					}
				}
				
				System.out.println("Importing album <" + a.getName() + ">");
			}
		}
		
		checkForNewSongs();
	}
	
	public void checkForNewSongs() {
		for(File f : core.getDirectoryScanner().getFiles()) {
			processSong(f);
		}
		
		sortSongs();

		for(Song s : library.getSongs()) {
			createAlbumForSong(s);
		}
		
		sortAlbums();
		
		for(Album a : library.getAlbums()) {
			createCoverForAlbum(a);
		}
		
		saveLibrary();
	}
	
	public void processSong(File file) {
		Mp3File mp3 = null;
		Song song = null;
		String title = null;
		String album = null;
		String artist = null;
		String coverPath = null;
		int audioDuration = 0;
		
		if(!library.songPathExists(file.getAbsolutePath())) {
			try {
				mp3 = new Mp3File(file.getAbsolutePath());
			} catch (UnsupportedTagException | InvalidDataException	| IOException e1) {
				e1.printStackTrace();
			}
			
			if(mp3.hasId3v2Tag()) {
				if(mp3.getId3v2Tag().getTitle() == null) {
					title = mp3.getFilename();
				} else {
					title = mp3.getId3v2Tag().getTitle();
				}
				if(mp3.getId3v2Tag().getAlbum() == null) {
					album = "Unknown Album";
				} else {
					album = mp3.getId3v2Tag().getAlbum();
				}
				if(mp3.getId3v2Tag().getAlbumArtist() == null) {
					artist = "Unknown Artist";
				} else {
					artist = mp3.getId3v2Tag().getAlbumArtist();
				}
				if(mp3.getLength() == 0) {
					audioDuration = getDurationWithMp3Spi(file);
				} else {
					audioDuration = Utils.safeLongToInt(mp3.getLengthInMilliseconds());
				}
			} else if(mp3.hasId3v1Tag()) {
				if(mp3.getId3v1Tag().getTitle() == null) {
					title = mp3.getFilename();
				} else {
					title = mp3.getId3v1Tag().getTitle();
				}
				if(mp3.getId3v1Tag().getAlbum() == null) {
					album = "Unknown Album";
				} else {
					album = mp3.getId3v1Tag().getAlbum();
				}
				if(mp3.getId3v1Tag().getArtist() == null) {
					artist = "Unknown Artist";
				} else {
					artist = mp3.getId3v1Tag().getArtist();
				}
				if(mp3.getLength() == 0) {
					audioDuration = getDurationWithMp3Spi(file);
				} else {
					audioDuration = Utils.safeLongToInt(mp3.getLengthInMilliseconds());
				}
			}
			
			song = new Song(title, artist, album, coverPath, audioDuration, file.getAbsolutePath());
			addSongToLibrary(song);
		}
	}
	
	public static int getDurationWithMp3Spi(File file) {
	    AudioFileFormat fileFormat = null;
		try {
			fileFormat = AudioSystem.getAudioFileFormat(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	    if (fileFormat instanceof TAudioFileFormat) {
	        Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
	        String key = "duration";
	        Long microseconds = (Long) properties.get(key);
	        int mili = (int) (microseconds / 1000);
	        
	        return mili;
	    } else {
	        try {
				throw new UnsupportedAudioFileException();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
	    }
	    
	    return 0;
	}
	
	public void sortSongs() {
		Collections.sort(library.getSongs(), new Comparator<Song>() {
			@Override
			public int compare(Song s1, Song s2) {
				return Collator.getInstance().compare(s1.getName(), s2.getName());
			}
	     });
	}
	
	public void createAlbumForSong(Song song) {
		if(!library.albumExists(song.getAlbum())) {
			Album album = new Album(song.getAlbum(), song.getArtist(), song.getCoverPath());
			System.out.println(album.getName() + ":");
			
			addSongsToAlbum(album);
			
			library.addAlbum(album);
		}
	}
	
	public void addSongsToAlbum(Album album) {
		for(Song s : library.getSongs()) {
			if(s.getAlbum().equals(album.getName())) {
				album.addSong(s);
				System.out.println("         " + s.getName());
			}
		}
	}
	
	public void sortAlbums() {
		Collections.sort(library.getAlbums(), new Comparator<Album>() {
			@Override
			public int compare(Album a1, Album a2) {
				return Collator.getInstance().compare(a1.getName(), a2.getName());
			}
	     });
	}
	
	public void createCoverForAlbum(Album album) {
		Song song = null;
		Mp3File mp3 = null;
		BufferedImage img = null;
		File coverFile = null;
		
		song = album.getSongs().get(0);
		try {
			mp3 = new Mp3File(song.getPath());
		} catch (UnsupportedTagException | InvalidDataException | IOException e1) {
			System.out.println(song.getPath());
			e1.printStackTrace();
		}
		if(album.getCoverPath() == null && mp3.getId3v2Tag().getAlbumImage() != null) {	
			try {
				img = ImageIO.read(new ByteArrayInputStream(mp3.getId3v2Tag().getAlbumImage()));
				coverFile = new File(new File(song.getPath()).getParent() + "//" + album.getName().replace(".", "").replace("?", "") + ".jpeg");
				ImageIO.write(img, "jpeg", coverFile);
				album.setCoverPath(coverFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(Song s : album.getSongs()) {
				s.setCoverPath(coverFile.getAbsolutePath());
			}
			
			System.out.println("Cover for album <" + album.getName() + "> has been created!");
		}
	}
	
	public void changeSongAlbum(Song song, Album newAlbum) {
		newAlbum.addSong(song);
		newAlbum.sortSongs();
		if(newAlbum.getCoverPath() != null)
			if(new File(newAlbum.getCoverPath()).exists())
				new File(newAlbum.getCoverPath()).delete();
		newAlbum.setCoverPath(null);
	}
	
	public void removeSongFromAlbum(Song song, Album album) {
		album.removeSong(song);
		album.sortSongs();
	}
	
	public void addSongToLibrary(Song song) {
		library.addSong(song);
	}
	
	public void addAlbumToLibrary(Album album) {
		library.addAlbum(album);
	}
	
	public void removeAlbumFromLibrary(Album album) {
		library.removeAlbum(album);
	}
	
	public void saveLibrary() {
		data.writeData("Library", getLibrary());
		System.out.println("Library has been saved!");
	}

	public void clearLibrary() {
		library.clear();
	}
	
	public Library getLibrary() {
		return library;
	}

}
