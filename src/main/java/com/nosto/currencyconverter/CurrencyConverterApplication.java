package com.nosto.currencyconverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * <h1>Nosto Currency Converter</h1>
 * @author Mahaboob Subahan
 * @since 30 August 2021
 * @version 0.0.1
 * Mobile: +358 466182902
 * Whatsapp: +91 979160022
 * Gmail: subahanih@gmail.com
 * Git: https://wwww.github.com/subahanih
 * Linkedin: https://www.linkedin.com/in/mahaboob-subahan
 * 
 * <p>
 * CurrencyConverterApplication.java:  
 * This class will initiate the Currency Converter application.
 * 
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.nosto.currencyconverter"})
public class CurrencyConverterApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterApplication.class, args);
	}
	
	/**
	 * Customization of Prometheus properties.
	 * @param applicationName
	 * @return applicationName
	 */
	@Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Value("${spring.application.name}") 
    		String applicationName) {
        return registry -> registry.config().commonTags("application", applicationName);
    }
	
}
