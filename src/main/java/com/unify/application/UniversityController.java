package com.unify.application;

import com.unify.application.dto.UniversityDTO;
import com.unify.domain.University;
import com.unify.persistence.UniversityRepository;
import com.unify.utilities.InvalidParamException;
import com.unify.utilities.ResourceNotFoundException;
import com.unify.utilities.UniversityAlreadyExistsException;


public class UniversityController {

	public UniversityDTO createUniversity(String uniId) throws UniversityAlreadyExistsException, InvalidParamException{
		checkUniNotExists(uniId);			
		University university=new University(uniId);
		UniversityRepository.saveUniversity(university);
		
		return new UniversityDTO(university);
	
	}
			
	private void checkUniNotExists(String uniId) throws UniversityAlreadyExistsException, InvalidParamException {		
		if(UniversityRepository.findUniversity(uniId)!=null){
			throw new UniversityAlreadyExistsException();
		}
	}

	University getUniversity(String uniId) throws InvalidParamException, ResourceNotFoundException{
		if(uniId==null || uniId.equals("")) throw new InvalidParamException();
		University uni=UniversityRepository.findUniversity(uniId);		
		if(uni==null) throw new ResourceNotFoundException();
		return uni;
	}
	
	void saveUniversity(University uni) throws InvalidParamException, ResourceNotFoundException{
		UniversityRepository.saveUniversity(uni);				
	}	
}
