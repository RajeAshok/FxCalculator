package com.anz.fx.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.exception.UnSupportedCurrencyException;
import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;
import com.anz.fx.service.CrossViaCurrencyLookUpService;
import com.anz.fx.service.ExchangeRateLoaderService;

public class FXCalculatorServiceImplTest {
	
	
	private static FXCalculatorServiceImpl fxCalculatorServiceImpl;
	
	@Mock
	private CrossViaCurrencyLookUpService crossViaCurrencyLookUpService;

	@Mock
	private ExchangeRateLoaderService exchangeRateLoaderService;
	

	
	@Before
	public void setUp(){
		
		MockitoAnnotations.initMocks(this);
		fxCalculatorServiceImpl = new FXCalculatorServiceImpl();
		
		Map<CurrencyPair, Double>baseTermCurrencyExchangeRateMap = new HashMap<>();
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("AUD", "USD"),0.8371);
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("USD", "AUD"),1.1946);
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("GBP", "USD"),1.5683);
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("EUR", "USD"),1.2315);
		baseTermCurrencyExchangeRateMap.put(new CurrencyPair("EUR", "NOK"),8.6651);
		
		Whitebox.setInternalState(fxCalculatorServiceImpl, "exchangeRateLoaderService", exchangeRateLoaderService);
		Mockito.when(exchangeRateLoaderService.fetchBaseCurrencyExchangeRateMap()).thenReturn(baseTermCurrencyExchangeRateMap);
		
		//loading Direction lookup Map
		CurrencyPair  usdAud = new CurrencyPair("USD", "AUD");
		CurrencyPair  audUsd = new CurrencyPair("AUD", "USD");
		
		CurrencyPair  gbpUsd = new CurrencyPair("GBP", "USD");
		CurrencyPair  usdGbp = new CurrencyPair("USD", "GBP");
		
		CurrencyPair  eurUsd = new CurrencyPair("EUR", "USD");
		CurrencyPair  usdEur = new CurrencyPair("USD", "EUR");
		
		CurrencyPair  eurNok = new CurrencyPair("EUR", "NOK");
		CurrencyPair  nokEur = new CurrencyPair("NOK", "EUR");

		Map<CurrencyPair,String> directionLookUpMap=new HashMap<>();
		directionLookUpMap.put(usdAud,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(audUsd,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(usdGbp,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(gbpUsd,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(usdEur,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(eurUsd,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(nokEur,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(eurNok,LookUpType.DIRECT.getValue());
		
		
		Whitebox.setInternalState(fxCalculatorServiceImpl, "crossViaCurrencyLookUpService", crossViaCurrencyLookUpService);
		Mockito.when(crossViaCurrencyLookUpService.fetchDirectIndirectCurrLookUpMap()).thenReturn(directionLookUpMap);
		
		//loading CrossViaCurrency Map
		Map<String,List<CurrencyPair>> crossViaCurrencyLookUpMap = new HashMap<>();
		List<CurrencyPair> viaUSDCurrencyPairList = new ArrayList<>();
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "CAD"));
		viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "AUD"));
		viaUSDCurrencyPairList.add(new CurrencyPair("GBP", "NOK"));
		viaUSDCurrencyPairList.add(new CurrencyPair("NOK", "GBP"));
		

		List<CurrencyPair> viaEURCurrencyPairList = new ArrayList<>();
		viaEURCurrencyPairList.add(new CurrencyPair("NOK", "USD"));
		viaEURCurrencyPairList.add(new CurrencyPair("USD", "NOK"));
		crossViaCurrencyLookUpMap.put("USD", viaUSDCurrencyPairList);
		crossViaCurrencyLookUpMap.put("EUR", viaEURCurrencyPairList);
		
		Mockito.when(crossViaCurrencyLookUpService.fetchCrossViaCurrencyLookUpMap()).thenReturn(crossViaCurrencyLookUpMap);
		
		List<Currency> supportedFXCurrenciesList = new ArrayList<>();
		supportedFXCurrenciesList.add(Currency.getInstance("AUD"));
		supportedFXCurrenciesList.add(Currency.getInstance("USD"));
		supportedFXCurrenciesList.add(Currency.getInstance("GBP"));
		supportedFXCurrenciesList.add(Currency.getInstance("EUR"));
		supportedFXCurrenciesList.add(Currency.getInstance("NOK"));
		
		Mockito.when(crossViaCurrencyLookUpService.fetchSupportedFXCurrenciesList()).thenReturn(supportedFXCurrenciesList);
		
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
		assertEquals(1.1946, fxCalculatorServiceImpl.computeExchangeRate(lookUpType, baseTermCurrencyPair), 0.1);
	}
	
	@Test
	public void fetchCurrencyPairLookUpTypeTest(){
	 CurrencyPair currencyPairTestInput = new CurrencyPair("USD", "AUD");
	 assertEquals(LookUpType.INVERSE.getValue(),fxCalculatorServiceImpl.fetchCurrencyPairLookUpType(currencyPairTestInput));
	 currencyPairTestInput = new CurrencyPair("AUD", "USD");
	 assertEquals(LookUpType.DIRECT.getValue(),fxCalculatorServiceImpl.fetchCurrencyPairLookUpType(currencyPairTestInput));
	}
	
	@Test(expected=FXDetailValidationException.class)
	public void validateBaseTermCurrenciesTestThrowsFXDetailValidationExceptionForBaseCurrencyCode() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "";
		String termCurrencyCode ="USD";
		fxCalculatorServiceImpl.validateBaseTermCurrencies(baseCurrencyCode, termCurrencyCode);
	}
	
	@Test(expected=FXDetailValidationException.class)
	public void validateBaseTermCurrenciesTestThrowsFXDetailValidationExceptionForTermCurrencyCode() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "USD";
		String termCurrencyCode ="";
		fxCalculatorServiceImpl.validateBaseTermCurrencies(baseCurrencyCode, termCurrencyCode);
	}
	
	@Test(expected=FXDetailValidationException.class)
	public void validateBaseTermCurrenciesTestThrowsFXDetailValidationExceptionForBaseCurrencyCodeNullValue() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = null;
		String termCurrencyCode ="USD";
		fxCalculatorServiceImpl.validateBaseTermCurrencies(baseCurrencyCode, termCurrencyCode);
	}
	
	@Test(expected=FXDetailValidationException.class)
	public void validateBaseTermCurrenciesTestThrowsFXDetailValidationExceptionForTermCurrencyCodeNullValue() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "USD";
		String termCurrencyCode =null;
		fxCalculatorServiceImpl.validateBaseTermCurrencies(baseCurrencyCode, termCurrencyCode);
	}
	
	@Test(expected=UnSupportedCurrencyException.class)
	public void validateBaseTermCurrenciesTestThrowsUnSupportedCurrencyExceptionForTermCurrencyCode() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "USD";
		String termCurrencyCode ="CRC";
		fxCalculatorServiceImpl.validateBaseTermCurrencies(baseCurrencyCode, termCurrencyCode);
		
	}
	@Test(expected=UnSupportedCurrencyException.class)
	public void validateBaseTermCurrenciesTestThrowsUnSupportedCurrencyExceptionForbaseCurrencyCode() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "CRC";
		String termCurrencyCode ="USD";
		fxCalculatorServiceImpl.validateBaseTermCurrencies(baseCurrencyCode, termCurrencyCode);
		
	}
	
	@Test(expected=FXDetailValidationException.class)
	public void checkIfBaseTermCurrencyIsSupportedTestThrowsFXValidationExceptionForTermCurrencyCode() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "USD";
		String termCurrencyCode ="WAK";
		fxCalculatorServiceImpl.checkIfBaseTermCurrencyIsSupported(baseCurrencyCode, termCurrencyCode);
		
	}
	@Test(expected=FXDetailValidationException.class)
	public void checkIfBaseTermCurrencyIsSupportedTestThrowsFXValidationExceptionForBaseCurrencyCode() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "WAK";
		String termCurrencyCode ="USD";
		fxCalculatorServiceImpl.checkIfBaseTermCurrencyIsSupported(baseCurrencyCode, termCurrencyCode);
		
	}
	
	@Test
	public void validateBaseTermCurrenciesTesthouldNotThrowUnSupportedCurrencyException() throws FXDetailValidationException, UnSupportedCurrencyException{
		String baseCurrencyCode = "USD";
		String termCurrencyCode ="AUD";
		fxCalculatorServiceImpl.validateBaseTermCurrencies(baseCurrencyCode, termCurrencyCode);
		
	}
	
	
	@Test
	public void formatConvertedAmountTest(){
		
		BigDecimal amountToFormat = new BigDecimal(100.4562);
		String termCurrencyCode ="AUD";
		Currency termCurrency = Currency.getInstance(termCurrencyCode);
		assertEquals(100.46, fxCalculatorServiceImpl.formatConvertedAmount(amountToFormat, termCurrency).doubleValue(), 0.1);
		
		termCurrencyCode ="JPY";
		termCurrency = Currency.getInstance(termCurrencyCode);
		assertEquals(101,fxCalculatorServiceImpl.formatConvertedAmount(amountToFormat, termCurrency).doubleValue(),0.1);
		
	}
	
	@Test
	public void computeFXAmountTest() throws FXDetailValidationException, UnSupportedCurrencyException{
		
		assertEquals(83.71,fxCalculatorServiceImpl.calculateFXAmount("AUD", "USD", new BigDecimal(100.00)).doubleValue(),0.1);
		assertEquals(110.35,fxCalculatorServiceImpl.calculateFXAmount("GBP", "NOK", new BigDecimal(10.00)).doubleValue(),0.1);
	}
	
	@Test
	public void displayResponseMessageTest(){
		String expectedResponse ="USD 10.00 = AUD 11.95";
		String baseCurrencyCode="USD";
		String termCurrencyCode="AUD";
		BigDecimal baseCurrAmt= new BigDecimal(10).setScale(Currency.getInstance(baseCurrencyCode).getDefaultFractionDigits(),RoundingMode.UP);
		BigDecimal termCurrAmt= new BigDecimal(11.95).setScale(Currency.getInstance(termCurrencyCode).getDefaultFractionDigits(),RoundingMode.UP);
		assertEquals(expectedResponse,fxCalculatorServiceImpl.displayResponseMessage(baseCurrencyCode, termCurrencyCode,baseCurrAmt, termCurrAmt));
		
		expectedResponse="USD 0.50 = AUD 0.12";
		baseCurrAmt= new BigDecimal(0.5).setScale(Currency.getInstance(baseCurrencyCode).getDefaultFractionDigits(),RoundingMode.UP);
		termCurrAmt= new BigDecimal(0.12).setScale(Currency.getInstance(termCurrencyCode).getDefaultFractionDigits(),RoundingMode.UP);
		assertEquals(expectedResponse,fxCalculatorServiceImpl.displayResponseMessage(baseCurrencyCode, termCurrencyCode,baseCurrAmt, termCurrAmt));
		
		baseCurrencyCode="AUD"	;
		termCurrencyCode="JPY";
		expectedResponse ="AUD 10.00 = JPY 1200";
	    baseCurrAmt= new BigDecimal(10).setScale(Currency.getInstance(baseCurrencyCode).getDefaultFractionDigits(),RoundingMode.UP);
		termCurrAmt= new BigDecimal(1200).setScale(Currency.getInstance(termCurrencyCode).getDefaultFractionDigits(),RoundingMode.UP);
		
		 assertEquals(expectedResponse,fxCalculatorServiceImpl.displayResponseMessage(baseCurrencyCode, termCurrencyCode,baseCurrAmt, termCurrAmt));
	}
	
	    @Test(expected=FXDetailValidationException.class)
		public void validateBaseCurrencyAmountTestThrowsException() throws FXDetailValidationException{
		   fxCalculatorServiceImpl.validateBaseCurrencyAmount("25.6S");
		}
		
	    @Test
		public void validateBaseCurrencyAmountTestDoesNotThrowException() throws FXDetailValidationException{
		   fxCalculatorServiceImpl.validateBaseCurrencyAmount("25.65");
		}
	    
	    public void computeFXConversionTest() throws FXDetailValidationException, UnSupportedCurrencyException{
	    	
	    	String baseCurrencyCode= "USD";
	    	String baseCurrencyAmountString="10.00";
	    	String termCurrencyCode= "AUD";
	    	String expectedResponse ="USD 10.00 = AUD 11.95";
	    	assertEquals(expectedResponse,fxCalculatorServiceImpl.computeFXConversion(baseCurrencyCode, baseCurrencyAmountString, termCurrencyCode));
	    	
	    	baseCurrencyCode= "USD";
	    	baseCurrencyAmountString="10.00";
	    	termCurrencyCode= "USD";
	    	expectedResponse ="USD 10.00 = USD 10.00";
	    	Whitebox.setInternalState(fxCalculatorServiceImpl, "sameBaseTermCurrency", true);
	    	assertEquals(expectedResponse,fxCalculatorServiceImpl.computeFXConversion(baseCurrencyCode, baseCurrencyAmountString, termCurrencyCode));
	    	
	    }
		
}
