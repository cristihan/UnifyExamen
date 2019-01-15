package com.unify.application;

import java.util.List;

import com.unify.application.dto.SongDTO;
import com.unify.domain.Song;
import com.unify.domain.University;
import com.unify.utilities.InvalidActionException;
import com.unify.utilities.InvalidParamException;
import com.unify.utilities.NoSongToPlayException;
import com.unify.utilities.ResourceNotFoundException;

public class SongController {

	private UniversityController uniController=new UniversityController();
	
	
	public SongDTO getCurrentSong(String uniId) throws NoSongToPlayException, InvalidParamException, ResourceNotFoundException {
		University uni=uniController.getUniversity(uniId);
		return new SongDTO(uni.getCurrentSong());
		
	}

	public SongDTO setCurrentSong(String uniId, SongDTO currentSong) throws InvalidParamException, ResourceNotFoundException, InvalidActionException{
		University uni=uniController.getUniversity(uniId);		
		Song song=uni.getSong(currentSong);
		uni.setCurrentSong(song);
		uniController.saveUniversity(uni);
		return new SongDTO(song);
	}

	public void addSongsToUniversity(String uniId, SongDTO song) throws InvalidParamException, ResourceNotFoundException {
		University uni=uniController.getUniversity(uniId);
		
		Song s = new Song(song.getTitle(),song.getArtist(),song.getDuration(),song.getSongUrl(),song.getImageUrl());
		uni.addSong(s);
	
		uniController.saveUniversity(uni);		
	}

	public List<Song> getAllSongs(String uniId) throws InvalidParamException, ResourceNotFoundException {
		University uni=uniController.getUniversity(uniId);
		return uni.getAllSongs();
	}
}
