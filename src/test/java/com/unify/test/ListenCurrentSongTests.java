package com.unify.test;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import com.unify.application.dto.SongDTO;
import com.unify.application.dto.UniversityDTO;
import com.unify.domain.Song;
import com.unify.domain.University;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class ListenCurrentSongTests extends AbstractTests{
	// FEATURE: As a user, I want to listen to my university's current song, so
	// I can discover awesome music.

	public ListenCurrentSongTests() {
		super();
	}
	
	
	//SCENARIO 1:
		
	@Given("^\"([^\"]*)\" is playing:$")
	public void is_playing(String uniId, List<SongDTO> songs) throws Throwable {
		clearBBDD();
		
		UniversityDTO university=tryToCreateUniversity(uniId);
		service.addSongInUniversity(songs.get(0), university.getId());		
		playSong(university, songs.get(0));		
	}


	@When("^user searches for university \"([^\"]*)\" current song$")
	public void user_searches_for_university_current_song(String uniId) throws Throwable {
		currentSong=searchForCurrentSong(uniId);
	}

	private SongDTO currentSong;
	
	@Then("^will return a song:$")
	public void will_return_a_song(List<SongDTO> songs) throws Throwable {
		SongDTO song=songs.get(0);	
		assertThat("Song not correct ",song.getTitle().equals(currentSong.getTitle()));
	}
	

	//SCENARIO 2:
	
	int responseCode;
	@Given("^\"([^\"]*)\" is not playing any song$")
	public void is_not_playing_any_song(String uniId) throws Throwable {
		clearBBDD();
		
		tryToCreateUniversity(uniId);
	}
	
	@When("^user searches for university's \"([^\"]*)\" current song$")
	public void user_searches_for_university_s_current_song(String uniId) throws Throwable {
		responseCode=service.getCurrentSongResponseCode(uniId);
	}


	@Then("^Not Found error will be returned$")
	public void not_Found_error_will_be_returned() throws Throwable {
		assertThat("Not found (404) error after asking for current song", responseCode==404);
	}

	
	//SCENARIO 3:
	
	@Given("^\"([^\"]*)\" is playing a song:$")
	public void is_playing_a_song(String uniId, List<SongDTO> songs) throws Throwable {
		clearBBDD();
		
		
		UniversityDTO university=tryToCreateUniversity(uniId);		
		for(SongDTO s: songs){
			service.addSongInUniversity(s, university.getId());
		}
		playSong(university, songs.get(0));
	}
	
	@Given("^System waits for \"([^\"]*)\" seconds$")
	public void system_waits_for_seconds(int seconds) throws Throwable {
	    Thread.sleep(seconds*1000);
	}


	@When("^user searches for \"([^\"]*)\" song$")
	public void user_searches_for_song(String uniId) throws Throwable {
		responseCode=service.getCurrentSongResponseCode(uniId);
	}
	
	@Then("^Not Found error will be shown$")
	public void not_Found_error_will_be_shown() throws Throwable {
		assertThat("Should return a not found (404) error. But is " + responseCode, responseCode==404);
		
	}


}
