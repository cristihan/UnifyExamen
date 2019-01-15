package com.unify.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="Already playing a song")
public class InvalidActionException extends Exception{

	
	private static final long serialVersionUID = 1L;
	
	public InvalidActionException(){
		
	}

}
