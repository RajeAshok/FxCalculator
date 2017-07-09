package com.anz.fx.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;
import com.anz.fx.util.ExchangeRateLoader;

public class FXCalculatorServiceImplTest {
	
	
	private static FXCalculatorServiceImpl fxCalculatorServiceImpl;
	
	@Mock
	private ExchangeRateLoader exchangeRateLoader;
	
	@Mock
	private  Map<CurrencyPair, Double> baseTermCurrencyExchangeRateMap; 
	
	@BeforeClass
	public static void setUpBeforeClass(){
	  
	}
	
	@Before
	public void setUp(){
		
		MockitoAnnotations.initMocks(this);
		fxCalculatorServiceImpl = new FXCalculatorServiceImpl();
		
		baseTermCurrencyExchangeRateMap = new HashMap<>();
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("AUD", "USD"),0.8371);
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("USD", "AUD"),1.1946);
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("GBP", "USD"),1.5683);
		Whitebox.setInternalState(fxCalculatorServiceImpl, "baseTermCurrencyExchangeRateMap", baseTermCurrencyExchangeRateMap);
		 
		  
	}

	
	@Test
	public void checkForSameBaseTermCurrencyTest(){
		String baseCurrencyCode="AUD";
		String termCurrencyCode ="AUD";
		assertTrue(fxCalculatorServiceImpl.checkForSameBaseTermCurrency(baseCurrencyCode, termCurrencyCode));
		baseCurrencyCode="CAD";
		termCurrencyCode ="AUD";
		assertFalse(fxCalculatorServiceImpl.checkForSameBaseTermCurrency(baseCurrencyCode, termCurrencyCode));
	}
	
	@Test
	public void computeExchangeRateTest(){

		CurrencyPair baseTermCurrencyPair = new CurrencyPair("USD","AUD");
		String lookUpType = LookUpType.INVERSE.getValue();		
		assertEquals(0.8371, fxCalculatorServiceImpl.computeExchangeRate(lookUpType, baseTermCurrencyPair), 0.1);
	}
	
	
}
