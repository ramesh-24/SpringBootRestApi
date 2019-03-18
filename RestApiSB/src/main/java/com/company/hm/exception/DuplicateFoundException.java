package com.company.hm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Entry point for this project.
 *
 * @author Gerald AJ
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DuplicateFoundException() {
		super();
	}

	public DuplicateFoundException(String exception) {
		super(exception);
	}

}