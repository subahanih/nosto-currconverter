package com.nosto.currencyconverter.model;

import java.util.Map;

/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * Error.java is a model for currency converter API
 */
public class Error {
	
	private Map<String, String> error;

	public Error() {
		super();
	}

	public Error(Map<String, String> error) {
		super();
		this.error = error;
	}

	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Error {" +
					"error=" + error + "}";
	}
	
}
