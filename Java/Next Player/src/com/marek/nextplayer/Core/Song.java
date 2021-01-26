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
