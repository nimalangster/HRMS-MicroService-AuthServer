package com.microservice.authserver.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ResoureAlreadyExistsAdvice {

	@ResponseBody
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	@ResponseStatus(HttpStatus.FOUND)
	String ResourceNotFoundHandler(ResourceAlreadyExistsException ex) {
		return ex.getMessage();
	}
}