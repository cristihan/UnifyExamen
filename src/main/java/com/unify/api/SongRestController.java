package com.unify.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonSyntaxException;
import com.unify.application.SongController;
import com.unify.application.dto.SongDTO;
import com.unify.domain.Song;
import com.unify.utilities.InvalidActionException;
import com.unify.utilities.InvalidParamException;
import com.unify.utilities.NoSongToPlayException;
import com.unify.utilities.ResourceNotFoundException;
import com.unify.utilities.UniversityAlreadyExistsException;


@RestController
@RequestMapping("/universities/{uniId}/songs")
public class SongRestController {

	@RequestMapping(value="/current",method=RequestMethod.GET)	
	public String getCurrentSong(@PathVariable String uniId) throws UniversityAlreadyExistsException, NoSongToPlayException, InvalidParamException, ResourceNotFoundException{		
		
		SongDTO song=new SongController().getCurrentSong(uniId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(song);
	}
		
	@RequestMapping(value="/current",method=RequestMethod.PUT)
	public String playSong(@PathVariable String uniId,@RequestBody String jSong) throws InvalidParamException, ResourceNotFoundException, JsonSyntaxException, InvalidActionException{				
		SongDTO song=new Gson().fromJson(jSong,SongDTO.class);
		song=new SongController().setCurrentSong(uniId, song);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(song);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addSongInUniversity(@PathVariable String uniId,@RequestBody String jSong) throws InvalidParamException, ResourceNotFoundException{				
		
		SongDTO song=new Gson().fromJson(jSong,SongDTO.class);
				
		new SongController().addSongsToUniversity(uniId, song);
	}
	
	@RequestMapping(method=RequestMethod.GET)	
	public String getAllSongs(@PathVariable String uniId) throws UniversityAlreadyExistsException, NoSongToPlayException, InvalidParamException, ResourceNotFoundException{		
		
		List<Song> songs=new SongController().getAllSongs(uniId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(songs);
	}
	
}
