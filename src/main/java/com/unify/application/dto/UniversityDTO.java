package com.unify.application.dto;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.unify.domain.Song;
import com.unify.domain.University;

public class UniversityDTO {

	
	@Expose
	private String id;	
	
	@Expose
	private List<SongDTO> allSongs=new ArrayList<SongDTO>();
	
	public UniversityDTO(University uni){
		this.id=uni.getId();				
		this.allSongs=convertToDTO(uni.getAllSongs());
		
	}

	private List<SongDTO> convertToDTO(List<Song> allSongs) {
		List<SongDTO> result=new ArrayList<>();
		for(Song s : allSongs){
			result.add(new SongDTO(s));
		}
		return result;
	}
	
	
	public String getId(){
		return this.id;
	}
	
}
