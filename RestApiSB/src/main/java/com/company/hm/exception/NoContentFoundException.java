package com.company.hm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Entry point for this project.
 *
 * @author Gerald AJ
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoContentFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public NoContentFoundException() {
		super();
	}

	public NoContentFoundException(String exception) {
		super(exception);
	}

}