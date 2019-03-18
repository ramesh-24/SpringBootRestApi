package com.company.hm.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Its a global exception handler for handling the exception at single point.
 *
 * @author Gerald AJ
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Custom exception to handle resource not found scenario. Single exception
	 * handler method for handling custom exceptions of Ingredient and NoContent
	 * found exception
	 * 
	 * 
	 * @param exception,
	 *            request
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler({ InternalServerException.class })
	public ResponseEntity<?> resourceNotFoundException(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date());
		errorDetails.setMessage(ex.getMessage());
		// = ErrorDetails.builder().timestamp(new Date()).message(ex.getMessage())
		// .details(request.getDescription(false)).build();
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Method to handle generic exceptions.
	 * 
	 * 
	 * @param exception,
	 *            request
	 * 
	 * @return ResponseEntity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTimestamp(new Date());
		errorDetails.setMessage(ex.getMessage());
		/*
		 * ErrorDetails errorDetails = ErrorDetails.builder().timestamp(new
		 * Date()).message(ex.getMessage())
		 * .details(request.getDescription(false)).build();
		 */
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}