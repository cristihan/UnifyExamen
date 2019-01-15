package com.unify.domain;

import java.util.Calendar;
import java.util.Date;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;

import com.google.gson.annotations.Expose;
import com.unify.persistence.CalendarConverter;
import com.unify.utilities.InvalidParamException;

@Embedded
@Converters(CalendarConverter.class)
public class Song {

	@Expose
	private String title;
	@Expose
	private String artistName;
	@Expose
	private int duration;
	@Expose
	private String songUrl;
	@Expose
	private String imageUrl;
	
	@Embedded
	private Date dateOfCurrentSong;
	
	public Song(){
		
	}
	public Song(String title, String artistName, int duration, String songUrl, String imageUrl) throws InvalidParamException{
		if(title == null || title.trim().equals(""))
			throw new InvalidParamException();
		if(artistName == null || artistName.trim().equals(""))
			throw new InvalidParamException();
		if(duration < 0)
			throw new InvalidParamException();
		if(songUrl == null || songUrl.trim().equals(""))
			throw new InvalidParamException();
		if(imageUrl == null || imageUrl.trim().equals(""))
			throw new InvalidParamException();
		
		this.title=title;
		this.artistName=artistName;
		this.duration=duration;
		this.songUrl=songUrl;
		this.imageUrl=imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public String getArtist() {
		return artistName;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public String getSongUrl(){
		return songUrl;
	}
	
	public String getImageUrl(){
		return imageUrl;
	}
	
	public void nowPlaying() {
		dateOfCurrentSong=Calendar.getInstance().getTime();		
	}
	
	public boolean hasTimeLeft() {
		int timePlaying=getSecDifference(Calendar.getInstance().getTime(), dateOfCurrentSong);
		return duration>=timePlaying; 
	}
	
	private int getSecDifference(Date now, Date dateOfInsert){
		return (int)(now.getTime()-dateOfInsert.getTime())/1000;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Song){
			Song s=(Song)obj;
			return s.artistName.equals(this.artistName)&&
					s.title.equals(this.title)&&
					s.songUrl.equals(this.songUrl)&&
					s.imageUrl.equals(this.imageUrl)&&
					s.duration==this.duration;
		}
		return false;
	}
}
