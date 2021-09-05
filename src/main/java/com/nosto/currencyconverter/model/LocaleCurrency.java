package com.nosto.currencyconverter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * LocaleCurrency.java is a pojo for currency converter application.
 * Creating table to H2 in memory data base.
 */
@Entity
@Table(name = "LOCALE_CURRENCY")
public class LocaleCurrency {
	
	@Id
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	@Column(name = "DISPLAY_NAME")
	private String displayName;

	@Column(name = "LANGUAGE_CODE")
	private String languageCode;

	public LocaleCurrency() {
		super();
	}

	public LocaleCurrency(String currencyCode, String countryCode, String displayName, String languageCode) {
		super();
		this.currencyCode = currencyCode;
		this.countryCode = countryCode;
		this.displayName = displayName;
		this.languageCode = languageCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	@Override
	public String toString() {
		return "CurrencyDetails [currencyCode=" + currencyCode + ", countryCode=" + countryCode + ", displayName="
				+ displayName + ", languageCode=" + languageCode + "]";
	}

}
