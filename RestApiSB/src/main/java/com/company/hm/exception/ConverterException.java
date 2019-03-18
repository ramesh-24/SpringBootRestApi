package com.company.hm.exception;

/**
 * Entry point for this project.
 *
 * @author Gerald AJ
 */

public class ConverterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConverterException() {
		super();
	}

	public ConverterException(String exception) {
		super(exception);
	}

}