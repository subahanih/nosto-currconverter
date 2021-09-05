package com.nosto.currencyconverter.model;

/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * ProcessedCurrency.java is a model for currency converter API
 * to provide properties for processed currency.
 */
public class ProcessedCurrency {
	
	private String timeTaken;
	
	private String sourceCurrency;
	
	private String targetCurrency;
	
	private double amount;
	
	private String convertedAmount;

	public ProcessedCurrency() {
		super();
	}

	public ProcessedCurrency(String timeTaken, String sourceCurrency, String targetCurrency, double amount,
			String convertedAmount) {
		super();
		this.timeTaken = timeTaken;
		this.sourceCurrency = sourceCurrency;
		this.targetCurrency = targetCurrency;
		this.amount = amount;
		this.convertedAmount = convertedAmount;
	}

	public String getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getConvertedAmount() {
		return convertedAmount;
	}

	public void setConvertedAmount(String convertedAmount) {
		this.convertedAmount = convertedAmount;
	}

	@Override
	public String toString() {
		return "ProcessedCurrency [timeTaken=" + timeTaken + ", sourceCurrency=" + sourceCurrency + ", targetCurrency="
				+ targetCurrency + ", amount=" + amount + ", convertedAmount=" + convertedAmount + "]";
	}
	
}
