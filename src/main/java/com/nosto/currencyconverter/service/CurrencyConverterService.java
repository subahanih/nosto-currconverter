package com.nosto.currencyconverter.service;

import static com.nosto.currencyconverter.util.CurrencyHelper.callURL;
import static com.nosto.currencyconverter.util.CurrencyValidator.validateCurrency;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosto.currencyconverter.exceptions.AmountException;
import com.nosto.currencyconverter.exceptions.CurrencyCodeEnception;
import com.nosto.currencyconverter.model.IncomingCurrency;
import com.nosto.currencyconverter.model.LocaleCurrency;
import com.nosto.currencyconverter.model.ProcessedCurrency;
import com.nosto.currencyconverter.repository.CurrencyDetailsRepository;

/**
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * CurrencyConverterService.java this class will provide service for currency converter.
 */
@Component
public class CurrencyConverterService {
	
	private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterService.class);
	
	@Autowired
	private CurrencyDetailsRepository currencyDetailsRepository;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss:ms:SSS");
	
	/**
	 * convertCurrency method is providing service to the controller.
	 * If given amount and currencies are valid then given amount would be calculated
	 * based on target currency value and amount symbol would also be converted to target currency.
	 * <ul>
	 * 	<li>firstly source currency will be validated</>
	 * 	 <ul><li>source currency should not be empty and should be in three characters, if more or less than 
	 * 		three characters then the CurrencyCodeEnception will be thrown</li></ul>
	 * <li>secondly target currency will be validated</>
	 * 	 <ul><li>target currency should not be empty and should be in three characters, if more or less than 
	 * 		three characters then CurrencyCodeEnception will be thrown</li></ul>
	 * <li>finally amount will be validated</>
	 * 	 <ul><li>amount should not be empty or non number or else AmountException will be thrown</li></ul>
	 * </ul>
	 * Once validation is done then only call would be sending to the source API. In case if currency code is not matching
	 * in source API then CurrencyCodeEnception will be thrown.
	 * <p>
	 * Converted amount would be converting to the Locale number format based on country code and language code.
	 * TO perform this operation, Java Locale has been used.
	 * <p><b>Note: Since, few of Locale country codes & language codes are not matching to the present scenario, all those
	 * code are uploaded manually with the help of H2 in-memory runtime database.</b>
	 * <p> Locale details will be read from CurrencyDetailsRepository and final formation will be done.
	 * In case Locale information is not available in repository also then result will be sent with the prefix of target
	 * currency code.
	 *   
	 * @param source
	 * @param target
	 * @param amount
	 * @return ProcessedCurrency
	 */
	@Cacheable(value = "currencyRate")
	public ProcessedCurrency convertCurrency(String source, String target, String amount) {
		logger.info("Inside convertCurrency()");
		long start = System.currentTimeMillis();
		IncomingCurrency incomingCurrency = null;
		ProcessedCurrency processedCurrency = null;
		double amountToConvert = 0.0;
		String result = null;
		
		// Before heading to source API, internally validating source and target currencies.
		if (validateCurrency(source)) {
			if(validateCurrency(target)) {
				if(amount != null) {
					try {
						amountToConvert = Double.parseDouble(amount);
					} catch (NumberFormatException e) {
						throw new AmountException("Please enter valid amount");
					}
					result = callURL(source,target);
				}
			} else {
				throw new CurrencyCodeEnception("You have provided invalid target Currency Code"); 
			}
		} else {
			throw new CurrencyCodeEnception("You have provided invalid source Currency Code"); 
		}		
		
		// Result from the source API is being converted in the form of IncomingCurrency model.
		if(result != null && result.contains("success")) {
			try {
				incomingCurrency = new ObjectMapper().readValue(result, IncomingCurrency.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} 

		// Checking given source & target currency codes are available in the Source API
		List<String> givenCurrency = incomingCurrency.getRates().keySet().stream().collect(Collectors.toList());
		if(!givenCurrency.isEmpty() && !givenCurrency.contains(source)) {
			throw new CurrencyCodeEnception("There is no information available for provided source Currency code");
		} else if (!givenCurrency.isEmpty() && !givenCurrency.contains(target)){
			throw new CurrencyCodeEnception("There is no information available for provided target Currency code");
		}

		// Currency calculation has been performed here with formula "(target/source)*amount"
		// Converted amount would be formated in target currency Locale number format(ex:￥ 39,417).
		if(incomingCurrency != null) {
			double convertedAmount = (((incomingCurrency.getRates().entrySet().stream()
					.filter(curr -> curr.getKey().equals(target)).findFirst().map(Map.Entry::getValue).get()) / 
				(incomingCurrency.getRates().entrySet().stream()
					.filter(curr -> curr.getKey().equals(source)).findFirst().map(Map.Entry::getValue).get())) * 
					amountToConvert);
			long end = System.currentTimeMillis();
			processedCurrency = new ProcessedCurrency(dateFormat.format(end-start), source, target, amountToConvert, 
				this.formatCurrency(target, convertedAmount));
		}
		return processedCurrency;
	}

	/**
	 * This function is responsible to format target currency in to Locale number format.
	 * In case Locale information is not available in the repository the the result
	 * will be send with suffix of target currency code(EX: JPY 254,30).
	 * @param targetCurrencyCode
	 * @param convertedAmount
	 * @return localeFormat
	 */
	private String formatCurrency(String targetCurrencyCode, double convertedAmount) {
		logger.info("Inside formatCurrency()");
		List<LocaleCurrency> listLocaleDetails = new ArrayList<>();
		currencyDetailsRepository.findAll().forEach(listLocaleDetails::add);
		LocaleCurrency localeDetails = listLocaleDetails.stream()
			.filter(currency -> currency.getCurrencyCode().equalsIgnoreCase(targetCurrencyCode))
			.findFirst().orElse(null);
		if(localeDetails == null) {
			return targetCurrencyCode + " " + convertedAmount;
		} else {
			NumberFormat localeFormat = NumberFormat.getCurrencyInstance(new Locale(localeDetails
				.getLanguageCode(), localeDetails.getCountryCode()));
			localeFormat.setCurrency(Currency.getInstance(localeDetails.getCurrencyCode()));
			return localeFormat.format(convertedAmount);
		}
	}

}
