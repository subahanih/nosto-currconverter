/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * CurrencyConverterServiceTests.java testing controller with positive and negative values. 
 */
package com.nosto.currencyconverter.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nosto.currencyconverter.exceptions.AmountException;
import com.nosto.currencyconverter.exceptions.CurrencyCodeEnception;
import com.nosto.currencyconverter.model.ProcessedCurrency;
import com.nosto.currencyconverter.service.CurrencyConverterService;

@WebMvcTest({CurrencyConverterController.class})
public class CurrencyConverterServiceTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CurrencyConverterService currencyConverterService;
     
    @Test
    @DisplayName("GET valid source=INR, valid target=GBP and amount=128.36 - OK")
    public void testWithValidSourceAndTargetCurrencies() throws Exception {
		ProcessedCurrency processedCurrency = 
			new ProcessedCurrency("00:00:00:005", "INR", "GBP", 128.36, "£12.49");
   	 	when(currencyConverterService.convertCurrency("INR", "GBP", "128.36")).thenReturn(processedCurrency);
   	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=INR&target=GBP&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isOk())
    		.andExpect(jsonPath("$.timeTaken", is("00:00:00:005")))
    		.andExpect(jsonPath("$.sourceCurrency", is("INR")))
    		.andExpect(jsonPath("$.targetCurrency", is("GBP")))
    		.andExpect(jsonPath("$.amount", is(128.36)))
    		.andExpect(jsonPath("$.convertedAmount", is("£12.49")))
    		.andReturn();
    }
    
    @Test
    @DisplayName("GET invalid source=III, valid target=GBP and amount=128.36 - NOT FOUND")
    public void testWithInvalidSourceAndValidTargetCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("III", "GBP", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("There is no information available for provided source Currency code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=III&target=GBP&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("There is no information available for provided source Currency code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET valid source=INR, invalid target=YYY and amount=128.36 - NOT FOUND")
    public void testWithValidSourceAndInvalidTargetCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("INR", "YYY", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("There is no information available for provided target Currency code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=INR&target=YYY&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("There is no information available for provided target Currency code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET invalid source=W, valid target=USD and amount=128.36 - NOT FOUND")
    public void testWithOneDigitInvalidSourceAndValidTargetCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("W", "USD", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid source Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=W&target=USD&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid source Currency Code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET invalid source=WREW, valid target=USD and amount=128.36 - NOT FOUND")
    public void testWithFourDigitInvalidSourceAndValidTargetCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("WREW", "USD", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid source Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=WREW&target=USD&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid source Currency Code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET valid source=GBP, invalid target=Y and amount=128.36 - NOT FOUND")
    public void testWithValidSourceAndOneDigitInvalidTargetCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("GBP", "Y", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid target Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=GBP&target=Y&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid target Currency Code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET valid source=GBP, invalid target=YYYY and amount=128.36 - NOT FOUND")
    public void testWithValidSourceAndFourDigitInvalidTargetCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("GBP", "YYYY", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid target Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=GBP&target=YYYY&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid target Currency Code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET invalid source=568, valid target=USD and amount=128.36 - NOT FOUND")
    public void testWithInvalidSourceAsNumberAndValidTargetCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("568", "USD", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid source Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=568&target=USD&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid source Currency Code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET valid source=GBP, invalid target=7512 and amount=128.36 - NOT FOUND")
    public void testWithValidSourceAndInvalidTargetAsNumberCurrencies() throws Exception {
   	 	when(currencyConverterService.convertCurrency("GBP", "7512", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid target Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=GBP&target=7512&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid target Currency Code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET valid source=GBP target=EUR and amount=EMPTY - NOT FOUND")
    public void testWithEmptyAmount() throws Exception {
   	 	when(currencyConverterService.convertCurrency("INR", "GBP", ""))
   	 		.thenThrow(new AmountException("Please enter valid amount"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=INR&target=GBP&amount=");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Amount Error")))
			.andExpect(jsonPath("$.message", is("Please enter valid amount")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET valid source=EMPY, invalid target=INR and amount=128.36 - NOT FOUND")
    public void testWithEmptySourceCurrency() throws Exception {
   	 	when(currencyConverterService.convertCurrency("", "INR", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid source Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=&target=INR&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid source Currency Code")))
	    	.andReturn();
    }
    
    @Test
    @DisplayName("GET valid source=GBP, invalid target=EMPTY and amount=128.36 - NOT FOUND")
    public void testWithEmptyTargetCurrency() throws Exception {
   	 	when(currencyConverterService.convertCurrency("GBP", "", "128.36"))
   	 		.thenThrow(new CurrencyCodeEnception("You have provided invalid target Currency Code"));
       	 	
    	RequestBuilder request = MockMvcRequestBuilders
			.get("/CurrencyConverter/ConvertCurrency?source=GBP&target=&amount=128.36");
    	
    	this.mvc.perform(request).andDo(print()).andExpect(status().isNotFound())
	    	.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.error", is("Currency Error")))
			.andExpect(jsonPath("$.message", is("You have provided invalid target Currency Code")))
	    	.andReturn();
    }
   
}
