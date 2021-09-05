package com.nosto.currencyconverter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nosto.currencyconverter.model.ProcessedCurrency;
import com.nosto.currencyconverter.service.CurrencyConverterService;

import io.micrometer.core.annotation.Timed;;

/**
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * CurrencyConverterController.java is the controller for currency converter API, 
 * it will be invoked by client. 
 */
@RestController
@RequestMapping(value = "/CurrencyConverter")
public class CurrencyConverterController {
	
	private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterController.class);
	
	@Autowired
	private CurrencyConverterService currencyConverterService;
	
	/**
	 * convertCurrency is the only GET method available in this API.
	 * Takes arguments as source currency, target currency and amount.
	 * Calculating the target currency for given amount then return the result with
	 * target currency symbol. 
	 * Calculation will be performed based on the value fetched from the 
	 * source API: http://api.exchangeratesapi.io
	 * @param source
	 * @param target
	 * @param amount
	 * @return ProcessedCurrency
	 */
	@Timed(value = "currency.convert.request", histogram = true, extraTags = {"Version", "1.0"})
	@GetMapping(value="/ConvertCurrency", produces = "application/json;charset=UTF-8")
	public ResponseEntity<ProcessedCurrency>  convertCurrency(String source, String target, String amount) {
		logger.info("Inside /ConvertCurrency");
		ProcessedCurrency convertedCurrency = currencyConverterService.convertCurrency(source, target, amount);
		return convertedCurrency != null 
			? new ResponseEntity<>(convertedCurrency, HttpStatus.OK)
			: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
