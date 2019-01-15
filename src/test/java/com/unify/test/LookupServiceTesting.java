package com.unify.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.json.simple.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.unify.application.dto.SongDTO;
import com.unify.application.dto.UniversityDTO;

@Service
public class LookupServiceTesting {

	RestTemplate restTemplate = new RestTemplate();
	private static String urlEndpoint = "http://localhost:8080/universities/";

	@Async
	public Future<SongDTO> getCurrentSong(String uniId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uniId", uniId);
		String result = restTemplate.getForObject(urlEndpoint + "{uniId}/songs/current/", String.class, params);
		Gson gson = new GsonBuilder().create();
		return new AsyncResult<SongDTO>(gson.fromJson(result, SongDTO.class));
	}

	@Async
	public void playSong(String uniId, SongDTO song) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uniId", uniId);
		restTemplate.put(urlEndpoint + "{uniId}/songs/current", toJson(song), params);
	}

	@Async
	public int getCurrentSongResponseCode(String uniId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uniId", uniId);
		removeErrorHandlerRestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(urlEndpoint + "{uniId}/songs/current/", HttpMethod.GET,
				null, String.class, params);
		restTemplate = new RestTemplate();
		return response.getStatusCode().value();
	}

	private void removeErrorHandlerRestTemplate() {
		restTemplate.setErrorHandler(new ResponseErrorHandler() {

			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				// TODO Auto-generated method stub

			}
		});
	}

	@Async
	public Future<UniversityDTO> createUniversity(String uniId) {
		JSONObject json = new JSONObject();
		json.put("uniId", uniId);
		String result = restTemplate.postForObject(urlEndpoint, json, String.class);
		return new AsyncResult<UniversityDTO>(new Gson().fromJson(result, UniversityDTO.class));
	}

	private String toJson(Object o) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(o);
	}

	@Async
	public Future<List<SongDTO>> getAllSongs(String uniId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uniId", uniId);
		String result = restTemplate.getForObject(urlEndpoint + "{uniId}/songs/", String.class, params);
		Gson gson = new GsonBuilder().create();
		List<SongDTO> songs = Arrays.asList(gson.fromJson(result, SongDTO[].class));
		return new AsyncResult<List<SongDTO>>(songs);
	}

	@Async
	public void addSongInUniversity(SongDTO song, String uniId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uniId", uniId);
		restTemplate.postForEntity(urlEndpoint + "{uniId}/songs/", toJson(song), String.class, params);

	}

	@Async
	public int addSongsInUniversityResponseCode(SongDTO song, String uniId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("uniId", uniId);
		removeErrorHandlerRestTemplate();

		ResponseEntity<String> response=restTemplate.postForEntity(urlEndpoint + "{uniId}/songs/", toJson(song), String.class, params);
		restTemplate = new RestTemplate();
		return response.getStatusCode().value();
	}

}
