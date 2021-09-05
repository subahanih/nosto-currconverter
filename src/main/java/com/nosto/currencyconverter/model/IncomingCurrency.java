package com.nosto.currencyconverter.model;

import java.util.Map;
/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * IncomingCurrency.java is a model for currency converter API
 * to provide properties from source API.
 */
public class IncomingCurrency {
	
	private boolean success;
	private int timestamp;
	private String base;
	private String date;
	private Map<String, Double> rates;
	
	public IncomingCurrency() {
		super();
	}
	
	public IncomingCurrency(boolean success, int timestamp, String base, String date, Map<String, Double> rates) {
		super();
		this.success = success;
		this.timestamp = timestamp;
		this.base = base;
		this.date = date;
		this.rates = rates;
	}

	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public Map<String, Double> getRates() {
		return rates;
	}
	
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "Currency {" +
					"success=" + success + 
					", timestamp=" + timestamp + 
					", base=" + base + 
					", date=" + date + 
					", rates=" + rates + 
				"}";
	}

}
