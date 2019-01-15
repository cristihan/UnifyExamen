package com.unify.test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unify.api.UnifyApplication;
import com.unify.application.dto.SongDTO;
import com.unify.application.dto.UniversityDTO;
import com.unify.domain.Song;
import com.unify.persistence.DatastoreManager;


@ContextConfiguration(classes = UnifyApplication.class, loader = SpringApplicationContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UnifyApplication.class)
@WebIntegrationTest
@EnableAsync
public class AbstractTests {
	
	public AbstractTests(){
		
	}
	
	protected LookupServiceTesting service = new LookupServiceTesting();
	
	protected void clearBBDD(){
		DatastoreManager.getInstance().clearBBDD();
	}

	protected UniversityDTO tryToCreateUniversity(String uniId) throws InterruptedException, ExecutionException {	
		
		Future<UniversityDTO>result=service.createUniversity(uniId);
		return result.get(); 
	}
		
	protected SongDTO searchForCurrentSong(String uniId) throws InterruptedException, ExecutionException {	
		return service.getCurrentSong(uniId).get();
	}
		
	protected void playSong(UniversityDTO uni, SongDTO song) {
		service.playSong(uni.getId(),song);
	}

	protected List<SongDTO> getAllSongsUniversity(String uniId) throws InterruptedException, ExecutionException {
		Future<List<SongDTO>> result=service.getAllSongs(uniId);
		return result.get();
	}
}

