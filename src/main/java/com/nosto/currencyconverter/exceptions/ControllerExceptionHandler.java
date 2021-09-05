package com.nosto.currencyconverter.exceptions;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.nosto.currencyconverter.exceptions.ErrorMessage;

/**
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * ControllerExceptionHandler.java is a handler.
 * This will advice to rest controller to act the way set up.
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
	
	private final static String CONFLICT_ERROR = "Conflict Error";
	private final static String NOT_FOUND_ERROR = "Currency Error";
	private final static String INVALID_AMOUNT_ERROR = "Amount Error";
	private final static String SERVER_ERROR = "Currency Server Error";
	
	/**
	 * sourceAPIException is configured to throw exception when
	 * any server or API error from source API. 
	 * @param exception
	 * @param request
	 * @return ErrorMessage
	 */
	 @ExceptionHandler(SourceAPIException.class)
	 @ResponseStatus(value = HttpStatus.CONFLICT)
	 public ErrorMessage sourceAPIException(SourceAPIException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
			new Date(),
			HttpStatus.CONFLICT.value(),
			CONFLICT_ERROR,
			exception.getMessage(),
			request.getDescription(false)
		);
		    
		return message;
	 }
	 
	 /**
	  * currencyNotFoundEnception is configured to throw exception
	  * when passing invalid currencies to the API 
	  * @param exception
	  * @param request
	  * @return ErrorMessage
	  */
	 @ExceptionHandler(CurrencyCodeEnception.class)
	 @ResponseStatus(value = HttpStatus.NOT_FOUND)
	 public ErrorMessage currencyNotFoundEnception(CurrencyCodeEnception exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
			new Date(),
			HttpStatus.NOT_FOUND.value(),
			NOT_FOUND_ERROR,
			exception.getMessage(),
			request.getDescription(false)
		);
		    
		return message;
	 }
	 
	 /**
	  * amountException is configured to throw exception
	  * when passing invalid amount to the API 
	  * @param exception
	  * @param request
	  * @return
	  */
	 @ExceptionHandler(AmountException.class)
	 @ResponseStatus(value = HttpStatus.NOT_FOUND)
	 public ErrorMessage amountException(AmountException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
			new Date(),
			HttpStatus.NOT_FOUND.value(),
			INVALID_AMOUNT_ERROR,
			exception.getMessage(),
			request.getDescription(false)
		);
		    
		return message;
	 }
	 
	 /**
	  * globalException is configured to throw any other exceptions
	  * that is not configured in this class.
	  * @param exception
	  * @param request
	  * @return
	  */
	 @ExceptionHandler(Exception.class)
	 @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	 public ErrorMessage globalException(Exception exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
			new Date(),
			HttpStatus.INTERNAL_SERVER_ERROR.value(),
			SERVER_ERROR,
			exception.getMessage(),
			request.getDescription(false)
		);
		    
		return message;
	 }

}
