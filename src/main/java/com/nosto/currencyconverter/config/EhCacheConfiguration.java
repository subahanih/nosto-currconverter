package com.nosto.currencyconverter.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Mahaboob Subahan
 * Date: 04 August 2021
 *
 * EhCacheConfiguration.java provides ehcache configuration to application. 
 */
@EnableCaching
@Configuration
public class EhCacheConfiguration {
	
	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(cacheManagerFactory().getObject());
	}
	
	@Bean
	public EhCacheManagerFactoryBean cacheManagerFactory() {
		EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
		bean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		bean.setShared(true);
		return bean;
	}

}
