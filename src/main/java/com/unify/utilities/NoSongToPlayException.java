package com.unify.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No song to play")
public class NoSongToPlayException extends Exception{

	
	private static final long serialVersionUID = 1L;
	
	public NoSongToPlayException(){
		
	}

}
