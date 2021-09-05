package com.nosto.currencyconverter.exceptions;

/**
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * SourceAPIException.java is a exception.
 * Will be thrown when receive source API error.
 */
public class SourceAPIException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SourceAPIException() {
		super();
	}
	
	public SourceAPIException (String message) {
		super(message);
	}

}
