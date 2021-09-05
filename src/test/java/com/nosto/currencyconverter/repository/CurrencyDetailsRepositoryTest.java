/**
 * @author Mahaboob Subahan
 * @since 02 September 2021
 *
 * CurrencyDetailsRepositoryTest.java testing repository. 
 */
package com.nosto.currencyconverter.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.nosto.currencyconverter.model.LocaleCurrency;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class CurrencyDetailsRepositoryTest {

	@Autowired
    private CurrencyDetailsRepository currencyDetailsRepository;
	
	@BeforeEach
    void initUseCase() {
        List<LocaleCurrency> currencyDetails = Arrays.asList(
            new LocaleCurrency("AED", "United Arab Emirates Dirham", "AE", "ar"),
            new LocaleCurrency("AFN", "Afghan Afghani", "AF", "fa"),
            new LocaleCurrency("ALL", "Albanian Lek", "AL", "sq"),
            new LocaleCurrency("AMD", "Armenian Dram", "AM", "hy")
        );
        currencyDetailsRepository.saveAll(currencyDetails);
    }

    @AfterEach
    public void destroyAll(){
    	currencyDetailsRepository.deleteAll();
    }
    
    @Test
    void findAll_success() {
        List<LocaleCurrency> allCurrency = (List<LocaleCurrency>) currencyDetailsRepository.findAll();
        assertThat(allCurrency.size()).isGreaterThanOrEqualTo(3);
    }
}
