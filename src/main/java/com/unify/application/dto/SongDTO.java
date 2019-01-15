package com.unify.application.dto;

import com.google.gson.annotations.Expose;
import com.unify.domain.Song;

public class SongDTO {

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

	public SongDTO(Song song) {

		this.title = song.getTitle();
		this.artistName = song.getArtist();
		this.duration = song.getDuration();
		this.songUrl = song.getSongUrl();
		this.imageUrl = song.getImageUrl();
	}

	public String getTitle() {
		return this.title;
	}

	public String getArtist() {
		return this.artistName;
	}

	public int getDuration() {
		return this.duration;
	}

	public String getSongUrl() {
		return this.songUrl;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SongDTO) {
			return this.title.equals(((SongDTO) obj).title);
		}
		return super.equals(obj);
	}

}
