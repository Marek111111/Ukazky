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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.swing.ImageIcon;
import javax.swing.Timer;

import javazoom.jl.decoder.JavaLayerException;

import com.marek.nextplayer.PlayBar;
import com.marek.nextplayer.Widget;

public class Player {
	
	private Core core;
	private PlayBar playbar;
	
	private final static int NOTSTARTED = 0;
    private final static int PLAYING = 1;
    private final static int PAUSED = 2;
    private final static int FINISHED = 3;
    
    private LinkedList<Song> playlist;
    public int track = 0;
    private Timer timer;
    private Song song;
    private javazoom.jl.player.Player player;
    private final Object playerLock = new Object();
    private int playerStatus = NOTSTARTED;
    private boolean shuffle;
    
    public Player(Core core) {
    	this.core = core;
    }
    
    public void setTrack(final Song song) {
    	core.getMainFrame().setTitle("Next Player - " + song.getArtist() + " - " + song.getName());
    	if(Variables.get(Variables.WIDGET_MODE).isTrue())
    		core.getWidget().setSong(song);
    	
    	this.song = song;
    	try {
			this.player = new javazoom.jl.player.Player(new FileInputStream(new File(getSong().getPath())));
			playbar.setPlayIcon(new ImageIcon("Images/pause.png"));
			if(Variables.get(Variables.WIDGET_MODE).isTrue())
				core.getWidget().setPlayIcon(new ImageIcon("Images/pause_widget.png"));
		} catch (FileNotFoundException | JavaLayerException e) {
			e.printStackTrace();
		}
    	
    	System.out.println("Playing track " + getSong().getName());
    	
		play();
    }
    
    
    public void setPlaylist(final LinkedList<Song> playlist) {
    	System.out.println("Playlist added " + playlist.get(0).getAlbum());
    	playbar.setPlayIcon(new ImageIcon("Images/pause.png"));
    	if(Variables.get(Variables.WIDGET_MODE).isTrue())
    		core.getWidget().setPlayIcon(new ImageIcon("Images/pause_widget.png"));
    	
    	if(playerStatus == NOTSTARTED) {
			setTrack(playlist.get(0));
			this.playlist = playlist;
		} else if(getPlaylist() != null) {
			if(getPlaylist().get(0).getAlbum() != playlist.get(0).getAlbum()) {
				track = 0;
				setTrack(playlist.get(track));
				this.playlist = playlist;
			}
		}
    	
    	if(timer == null)
	    	timer = new Timer(1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(playerStatus != PAUSED && playerStatus != PLAYING && playerStatus != NOTSTARTED) {
						if(!(track + 1 > getPlaylist().size()-1)) {
							if(getSong() != getPlaylist().get(track + 1)) {
								playerStatus = NOTSTARTED;
								next();							
							} else {
								track = 0;
								playerStatus = NOTSTARTED;
							}
						} else {
							track = 0;
							playerStatus = NOTSTARTED;
						}
					}
				}
			});
    	if(!timer.isRunning())
    		timer.start();
    }
    
    
    public void play() {
        synchronized (playerLock) {
            switch (playerStatus) {
                case NOTSTARTED:
                    final Runnable r = new Runnable() {
                        public void run() {
                            playInternal();
                        }
                    };
                    final Thread t = new Thread(r);
                    t.setDaemon(true);
                    t.setPriority(Thread.MAX_PRIORITY);
                    playerStatus = PLAYING;
                    t.start();
                    break;
                case PAUSED:
                	resume();
                    break;
                default:
                    break;
            }
        }
    }
    
    
    public void next() {
    	if(shuffle) {
    		int prevTrack = track;
    		int randomTrack = new Random().nextInt(getPlaylist().size());
    		if(randomTrack == prevTrack) {
    			next();
    		}
    		track = randomTrack;
    		setTrack(getPlaylist().get(track));
    		System.out.println("Playing random track: " + getPlaylist().get(track).getName());
			System.out.println("Track: " + track + " :: " + (getPlaylist().size()-1));
    	} else {
    		if(!(track + 1 > playlist.size()-1)) {
    			track += 1;
    			setTrack(getPlaylist().get(track));
    			System.out.println("Playing next track: " + getPlaylist().get(track).getName());
    			System.out.println("Track: " + track + " :: " + (getPlaylist().size()-1));
    		}
    	}
    	
    	core.getMainFrame().getSideBar().scrollTo(track);
    }
    
    public void prev() {
    	if(!(track - 1 < 0)) {
    		track -= 1;
    		setTrack(getPlaylist().get(track));
    		System.out.println("Playing previous track: " + getPlaylist().get(track).getName());
    	} else {
    		track = 0;
    		setTrack(getPlaylist().get(track));
    		System.out.println("Playing playlist " + getPlaylist().get(track).getAlbum() + " again.");
    	}
    	
    	core.getMainFrame().getSideBar().scrollTo(track);
    }

    
    public boolean pause() {
        synchronized (playerLock) {
            if (playerStatus == PLAYING) {
            	playbar.setPlayIcon(new ImageIcon("Images/play.png"));
            	if(Variables.get(Variables.WIDGET_MODE).isTrue())
            		core.getWidget().setPlayIcon(new ImageIcon("Images/play_widget.png"));
            	playerStatus = PAUSED;
            }
            return playerStatus == PAUSED;
        }
    }

    
    public boolean resume() {
        synchronized (playerLock) {
            if (playerStatus == PAUSED) {
            	playbar.setPlayIcon(new ImageIcon("Images/pause.png"));
            	if(Variables.get(Variables.WIDGET_MODE).isTrue())
            		core.getWidget().setPlayIcon(new ImageIcon("Images/pause_widget.png"));
            	playerStatus = PLAYING;
            	playerLock.notifyAll();
            }
            return playerStatus == PLAYING;
        }
    }

    
    public void stop() {
        synchronized (playerLock) {
        	playbar.setPlayIcon(new ImageIcon("Images/play.png"));
        	if(Variables.get(Variables.WIDGET_MODE).isTrue())
        		core.getWidget().setPlayIcon(new ImageIcon("Images/play_widget.png"));
        	playerStatus = FINISHED;
        	playerLock.notifyAll();
        }
    }
    
    public void setVolume(float value) {
    	try {
			Line line = AudioSystem.getLine(Port.Info.SPEAKER);
			line.open();
			
			FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
			volumeControl.setValue(value);
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
    }
    
    public float getVolume() {
    	try {
			Line line = AudioSystem.getLine(Port.Info.SPEAKER);
			line.open();
			
			FloatControl volumeControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
			return volumeControl.getValue();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
    	
    	return 100;
    }
    
    public void shuffleOn() {
    	shuffle = true;
    }
    
    public void shuffleOff() {
    	shuffle = false;
    }
    
    public boolean isShuffleOn() {
    	return shuffle;
    }
    
    private void playInternal() {
        while (playerStatus != FINISHED) {
            try {
                if (!player.play(1)) {
                	playbar.setPlayIcon(new ImageIcon("Images/play.png"));
                	if(Variables.get(Variables.WIDGET_MODE).isTrue())
                		core.getWidget().setPlayIcon(new ImageIcon("Images/play_widget.png"));
                    break;
                }
            } catch (final JavaLayerException e) {
                break;
            }
            synchronized (playerLock) {
                while (playerStatus == PAUSED) {
                    try {
                    	playbar.setPlayIcon(new ImageIcon("Images/play.png"));
                    	if(Variables.get(Variables.WIDGET_MODE).isTrue())
                    		core.getWidget().setPlayIcon(new ImageIcon("Images/play_widget.png"));
                    	playerLock.wait();
                    } catch (final InterruptedException e) {
                        break;
                    }
                }
            }
        }
        close();
    }
    
    public void close() {
        synchronized (playerLock) {
        	playerStatus = FINISHED;
        }
        try {
        	stop();
        } catch (final Exception e) {
        }
    }
    
    public boolean notStarted() {
    	if(playerStatus == NOTSTARTED)
    		return true;
    	
    	return false;
    }
    
    public boolean isPlaying() {
    	if(playerStatus == PLAYING)
    		return true;
    	
    	return false;
    }
    
    public boolean isPaused() {
    	if(playerStatus == PAUSED)
    		return true;
    	
    	return false;
    }
    
    
    public boolean hasFinished() {
    	if(playerStatus == FINISHED)
    		return true;
    	
    	return false;
    }
    
    public int getProgress() {
    	if(player != null)
    		return player.getPosition();
    	
    	return 0;
    }
    
    public Song getSong() {
    	return song;
    }

    public LinkedList<Song> getPlaylist() {
    	return playlist;
    }
    
    public void setPlayBar(PlayBar playbar) {
    	this.playbar = playbar;
    }
}
