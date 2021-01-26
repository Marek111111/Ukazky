

package com.marek.nextplayer.Core;

import java.util.LinkedList;

public class Library {
	
	private LinkedList<Song> songs;
	private LinkedList<Album> albums;
	
	public Library() {
		songs = new LinkedList<Song>();
		albums = new LinkedList<Album>();
	}
	
	public void addSong(Song song) {
		if(!songs.contains(song)) {
			songs.add(song);
			System.out.println("Song <" + song.getName() + "> from album <" + song.getAlbum() + "> has been added to music library!");
		}
	}
	
	public Song getSong(String name) {
		for(Song s : songs) {
			if(s.getName().equals(name)) {
				return s;
			}
		}
		
		return null;
	}
	
	public void addAlbum(Album album) {
		if(!albums.contains(album)) {
			albums.add(album);
			System.out.println("Album <" + album.getName() + "> has been added to music library! \n");
		}
	}
	
	public void removeAlbum(Album album) {
		if(albums.contains(album)) {
			albums.remove(album);
		}
	}
	
	public Album getAlbum(String name) {
		for(Album a : albums) {
			if(a.getName().equals(name)) {
				return a;
			}
		}
		
		return null;
	}
	
	public void clear() {
		songs.clear();
		albums.clear();
	}
	
	public boolean songExists(Song song) {
		return songs.contains(song);
	}
	
	public boolean songPathExists(String path) {
		for(Song s : songs) {
			if(s.getPath().equals(path))
				return true;
		}
		
		return false;
	}
	
	public boolean albumExists(String name) {
		for(Album a : albums) {
			if(a.getName().equals(name))
				return true;
		}
		
		return false;
	}
	
	public LinkedList<Song> getSongs() {
		return songs;
	}
	
	public LinkedList<Album> getAlbums() {
		return albums;
	}

}
