package com.company.hm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Entry point for this project.
 *
 * @author Gerald AJ
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ReceipeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReceipeNotFoundException(String exception) {
		super(exception);
	}

}