/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * CurrencyValidator.java this will help API to validate its base and target currencies. 
 */
package com.nosto.currencyconverter.util;

public class CurrencyValidator {
	
	/**
	 * This method will return true when base currency is alphabet 
	 * and currency code length is 3.
	 * @param currency
	 * @return boolean
	 */
	public static boolean validateCurrency(String currency) {
		return currency.trim().length() == 3 && isAlpha(currency) ? true : false;
	}
	
	/**
	 * This method will check that the given currency code contains only alphabets
	 * and it contains only alphabet then it will return true or it will return false.
	 * @param currency
	 * @return boolean
	 */
	private static boolean isAlpha(String currency) {
        return  currency != null && currency.chars().allMatch(Character::isLetter);
    }

}
