package com.anz.fx.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.model.CurrencyPair;

public class ExchangeRateLoaderServiceImplTest {

	@Autowired
	private ExchangeRateLoaderServiceImpl exchangeRateLoaderServiceImpl;
	
	private Map<CurrencyPair,Double> baseTermCurrencyExchangeRateMap =new HashMap<>();
	
	@Value("classpath:BaseTermCurrencyExchangeRates.txt")
	private Resource exchangeRateLookUpResource;
	
	@Before
	public void setUp() throws FXDetailValidationException{
		
		exchangeRateLoaderServiceImpl = Mockito.spy(new ExchangeRateLoaderServiceImpl());
		Whitebox.setInternalState(exchangeRateLoaderServiceImpl, "exchangeRateLookUpResource", exchangeRateLookUpResource);
		Whitebox.setInternalState(exchangeRateLoaderServiceImpl, "baseTermCurrencyExchangeRateMap", baseTermCurrencyExchangeRateMap);
		List<String> resourceFileStringList = new ArrayList<>();
		resourceFileStringList.add("AUD USD 0.8371");
		resourceFileStringList.add("CAD USD 0.8711");

		Mockito.doReturn(resourceFileStringList).when(exchangeRateLoaderServiceImpl).loadResourceFile();
		
	}
	
	@Test
	public void loadBaseTermCurrencyExchangeRatesForIOException() throws FXDetailValidationException{
		exchangeRateLoaderServiceImpl.loadBaseTermCurrencyExchangeRates();
		assertEquals(2,baseTermCurrencyExchangeRateMap.size());
	}

}
