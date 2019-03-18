package com.company.hm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Service unaailable due to internal server error
 *
 * @author Gerald AJ
 */
@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class InternalServerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InternalServerException() {
		super();
	}

	public InternalServerException(String exception) {
		super(exception);
	}

}