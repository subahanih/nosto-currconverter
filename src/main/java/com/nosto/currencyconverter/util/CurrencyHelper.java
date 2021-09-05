package com.nosto.currencyconverter.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosto.currencyconverter.exceptions.CurrencyCodeEnception;
import com.nosto.currencyconverter.exceptions.SourceAPIException;
import com.nosto.currencyconverter.model.Error;

/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * CurrencyHelper.java this will help API to validate its base and target currencies. 
 */
public class CurrencyHelper {

	private static String url = "http://api.exchangeratesapi.io/v1/latest?";
	private static String accessKey = "access_key=2dd7264289c7af03f99352391c31c300";

	/**
	 * callURL is responsible to perform get call to source API.
	 * <ul>
	 * <li>if requested currency code is not available then CurrencyCodeEnception will be thrown.
	 * <li>if API is not able to respond due to any other reason then SourceAPIException will be thrown. 
	 * </ul>
	 * @param source
	 * @param target
	 * @return
	 */
	public static String callURL(String source, String target) {
		StringBuilder result = new StringBuilder();
		HttpURLConnection urlConn;
		String errorMessage = null;
		try {
			urlConn = (HttpURLConnection) new URL(url + accessKey + "&base=EUR&symbols=" + source + "," + target)
					.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setRequestProperty("Accept-Charset", "UTF-8"); 
			
			// If any error occurred then result would be mapped to Error model.
			if (urlConn.getErrorStream() != null || urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getErrorStream()));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line).append('\n');
				}
				Error error = new ObjectMapper().readValue(result.toString(), Error.class);
				errorMessage = error.getError().entrySet().stream().findFirst().map(Map.Entry::getValue).get();
			}

			// If request went successful the the result would be send to caller.
			if (urlConn.getInputStream() != null && urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStreamReader in = new InputStreamReader(urlConn.getInputStream(), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(in);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					result.append(line);
				}
				in.close();
			}
		} catch (Exception e) {
			if (errorMessage != null && errorMessage.equalsIgnoreCase("invalid_currency_codes")) {
				// if requested currency code is not available then CurrencyCodeEnception will be thrown.
				throw new CurrencyCodeEnception(
						"There is no information available for provided source and target Currency codes.");
			} else {
				// if API is not able to respond due to any other reason then SourceAPIException will be thrown. 
				throw new SourceAPIException("Exception in source API");
			}
		}
		return result.toString();
	}

}
