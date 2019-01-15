package com.unify.persistence;

import org.mongodb.morphia.Datastore;

import com.unify.domain.University;
import com.unify.utilities.InvalidParamException;

public class UniversityRepository {

	private static Datastore datastore=DatastoreManager.getInstance().getDatastore();

	
	public static void saveUniversity(University university) throws InvalidParamException {
		if(university==null) throw new InvalidParamException();		
		datastore.save(university);
	}

	public static University findUniversity(String uniId) {					
		return datastore.find(University.class).field("id").equal(uniId).get();
	}

}
