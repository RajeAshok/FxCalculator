package com.anz.fx.service.impl;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.anz.fx.exception.FXDetailValidationException;

public class ExchangeRateLoaderServiceImplTest {

	@Autowired
	private ExchangeRateLoaderServiceImpl exchangeRateLoaderServiceImpl;
	
	@Value("classpath:BaseTermCurrencyExchangeRates.txt")
	private Resource exchangeRateLookUpResource;
	
	//private static final Path PATH = Paths.get("/tmp/foo");
	
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		exchangeRateLoaderServiceImpl = new ExchangeRateLoaderServiceImpl();
		
		URL url = this.getClass().getResource("/BaseTermCurrencyExchangeRates.txt");
		System.out.println("url..." + url);
	//	File file = new File(url.getFile());
		Whitebox.setInternalState(exchangeRateLoaderServiceImpl, "exchangeRateLookUpResource", exchangeRateLookUpResource);
		Mockito.when(exchangeRateLookUpResource.getURI()).thenReturn(url.toURI());
	}
	

	@Test(expected=FXDetailValidationException.class)
	public void loadBaseTermCurrencyExchangeRatesForIOException() throws FXDetailValidationException{
		exchangeRateLoaderServiceImpl.loadBaseTermCurrencyExchangeRates();
	}

}
