package com.microservice.authserver.exceptionhandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {

	public ResourceAlreadyExistsException(Long id,String resourceName) {
		super("Resource '" + resourceName +"' already exists with id " + id);
	}

	
}
