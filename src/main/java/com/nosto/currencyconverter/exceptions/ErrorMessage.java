package com.nosto.currencyconverter.exceptions;

import java.util.Date;

/**
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * ErrorMessage.java is a model.
 * It provides properties to ControllerExceptionHandler.
 */
public class ErrorMessage {
	
	private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String description;
    
    public ErrorMessage() {
    	
    }
    
    public ErrorMessage(Date timestamp, int status, String error, String message, String description) {
    	this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.description = description;
    }

	public Date getTimestamp() {
		return timestamp;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

}
