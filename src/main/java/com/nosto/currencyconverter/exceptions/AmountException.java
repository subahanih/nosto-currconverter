package com.nosto.currencyconverter.exceptions;

/**
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * AmountException.java is a exception.
 * Will be thrown when invalid amount.
 */
public class AmountException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AmountException() {
		super();
	}
	
	public AmountException (String message) {
		super(message);
	}

}
