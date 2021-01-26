

package com.marek.nextplayer.Core;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Album {
	
	private String name;
	private String artist;
	private String coverPath;
	private LinkedList<Song> songs = new LinkedList<Song>();
	
	public Album(String name, String artist, String coverPath) {
		this.name = name;
		this.artist = artist;
		this.coverPath = coverPath;
	}
	
	public void setName(String name) {
		this.name = name;
		for(Song s : getSongs()) {
			s.setAlbum(name);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
		for(Song s : getSongs()) {
			s.setArtist(artist);
		}
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
		for(Song s : getSongs()) {
			s.setCoverPath(coverPath);
		}
	}
	
	public String getCoverPath() {
		return coverPath;
	}

	public void addSong(Song song) {
		if(!songs.contains(getSongByName(song.getName()))) {
			songs.add(song);
		}
	}
	
	public void removeSong(Song song) {
		if(songs.contains(song))
			songs.remove(song);
	}
	
	public Song getSongByName(String songName) {
		for(Song s : songs) {
			if(s.getName().equals(songName))
				return s;
		}
		
		return null;
	}
	
	public void sortSongs() {
		Collections.sort(songs, new Comparator<Song>() {
			@Override
			public int compare(Song s1, Song s2) {
				return Collator.getInstance().compare(s1.getName(), s2.getName());
			}
	     });
	}
	
	public LinkedList<Song> getSongs() {
		return songs;
	}
	
	public String toString() {
		return getName() + getArtist() + getCoverPath() + songs;
	}
}
