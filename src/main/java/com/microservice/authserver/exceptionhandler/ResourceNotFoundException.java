package com.microservice.authserver.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(Long id,String resourceName) {
		super("Could not find '" + resourceName +"' with id " + id);
	}

	
}
