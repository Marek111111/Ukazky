

package com.marek.nextplayer.Core;

public class Song {
	
	private String name;
	private String artist;
	private String album;
	private String coverPath;
	private int audioDuration;
	private String path;
	
	public Song(String name, String artist, String album, String coverPath, int audioDuration, String path) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.coverPath = coverPath;
		this.audioDuration = audioDuration;
		this.path = path;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}
	
	public String getCoverPath() {
		return coverPath;
	}
	
	public int getAudioDuration() {
		return audioDuration;
	}
	
	public String getPath() {
		return path;
	}
	
	public String toString() {
		return getName() + getArtist() + getAlbum() + getCoverPath() + getAudioDuration() + getPath();
	}
}
