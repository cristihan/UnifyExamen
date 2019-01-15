package com.unify.domain;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.google.gson.annotations.Expose;
import com.unify.application.dto.SongDTO;
import com.unify.utilities.InvalidActionException;
import com.unify.utilities.InvalidParamException;
import com.unify.utilities.NoSongToPlayException;
import com.unify.utilities.ResourceNotFoundException;

@Entity("universities")
public class University {

	@Id
	@Expose
	private String id;
	@Embedded
	@Expose
	private Song currentSong;

	@Embedded
	private List<Song> allSongs = new ArrayList<Song>();

	public University() {

	}

	public University(String uniId) {
		this.id = uniId;
	}

	public void setCurrentSong(Song song) throws InvalidParamException, InvalidActionException {
		if (song == null)
			throw new InvalidParamException();
		if (currentSong != null && currentSong.hasTimeLeft())
			throw new InvalidActionException();
		this.currentSong = song;
		currentSong.nowPlaying();
	}

	public String getId() {
		return id;
	}

	public Song getCurrentSong() throws NoSongToPlayException {
		if(currentSong == null)
			throw new NoSongToPlayException();
		if (!currentSong.hasTimeLeft())
			throw new NoSongToPlayException();
		return currentSong;
	}

	public boolean containsSong(Song song) {
		return allSongs.contains(song);
	}


	public List<Song> getAllSongs() {
		return new ArrayList<>(allSongs);
	}

	public Song getSong(SongDTO song) throws ResourceNotFoundException {
		for (Song s : allSongs) {
			if (s.getTitle().equals(song.getTitle())) {
				return s;
			}
		}
		throw new ResourceNotFoundException();
	}

	public void addSong(Song s) {
		this.allSongs.add(s);
	}

}
