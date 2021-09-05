package com.nosto.currencyconverter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nosto.currencyconverter.model.LocaleCurrency; 

/**
 * 
 * @author Mahaboob Subahan
 * @since 30 August 2021
 *
 * CurrencyDetailsRepository.java this interface will provide CRUD repository services 
 * with respect to currency converter API.
 */
@Repository(value = "currencyDetailsRepository")
public interface CurrencyDetailsRepository extends CrudRepository<LocaleCurrency, Integer>{

}
