/**
 * @author Mahaboob Subahan
 * @since 31 August 2021
 *
 * CurrencyConverterApplicationTests.java testing controller. 
 */
package com.nosto.currencyconverter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nosto.currencyconverter.controller.CurrencyConverterController;

@SpringBootTest
class CurrencyConverterApplicationTests {
	
	@Autowired
	private CurrencyConverterController currencyConverterController;

	@Test
	void contextLoads() {
		 Assertions.assertThat(currencyConverterController).isNotNull();
	}

}
