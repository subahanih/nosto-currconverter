package com.nosto.currencyconverter.exceptions;

/**
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * CurrencyCodeEnception.java is a exception.
 * Will be thrown when requested currency is not in right format
 * and currency is not present in source API.
 */
public class CurrencyCodeEnception extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CurrencyCodeEnception() {
		super();
	}

	public CurrencyCodeEnception (String message) {
		super(message);
	}
}
