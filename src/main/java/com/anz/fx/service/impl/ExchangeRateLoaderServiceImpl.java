package com.anz.fx.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.model.CurrencyPair;
import com.anz.fx.service.ExchangeRateLoaderService;

@Service
public class ExchangeRateLoaderServiceImpl implements ExchangeRateLoaderService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateLoaderServiceImpl.class);
	
	 private Map<CurrencyPair,Double> baseTermCurrencyExchangeRateMap =new HashMap<>();
	 
	 @Value("classpath:BaseTermCurrencyExchangeRates.txt")
	 private  Resource exchangeRateLookUpResource;
	
	 @Override
	 public void loadBaseTermCurrencyExchangeRates() throws FXDetailValidationException {
		
		LOG.debug("loading exchange rates for a currency pair from flat file");
		List<String> baseTermCurrencyExchangRateLookUpDetails =null;

	    baseTermCurrencyExchangRateLookUpDetails=loadResourceFile();

		for (int i = 0; i < baseTermCurrencyExchangRateLookUpDetails.size(); i++) {
			String baseTermCurrencyExchRateString =baseTermCurrencyExchangRateLookUpDetails.get(i);

			String[] baseCurrencyExchangeRateElements = baseTermCurrencyExchRateString.split("\\s+");
			int baseTermExchangeRateRecordLength = baseCurrencyExchangeRateElements.length;
		
			String baseCurrencyCode = baseCurrencyExchangeRateElements[baseTermExchangeRateRecordLength-baseTermExchangeRateRecordLength];
			String termCurrencyCode = baseCurrencyExchangeRateElements[baseTermExchangeRateRecordLength -(baseTermExchangeRateRecordLength-1)];
			String exchangeRate = baseCurrencyExchangeRateElements[baseTermExchangeRateRecordLength-1];
			//Add the currencyPair in inverted order to have all mappings
			baseTermCurrencyExchangeRateMap.put(new CurrencyPair(baseCurrencyCode,termCurrencyCode), Double.parseDouble(exchangeRate));
		
		}		
		setBaseTermCurrencyExchangeRateMap(baseTermCurrencyExchangeRateMap);
		
	}
	
	protected List<String> loadResourceFile() throws FXDetailValidationException{
		List<String> baseTermCurrencyExchangRateLookUpDetails =null;
		try {
		  baseTermCurrencyExchangRateLookUpDetails = Files.readAllLines(Paths.get(exchangeRateLookUpResource.getURI()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new FXDetailValidationException("Error Reading File");
		}
		return baseTermCurrencyExchangRateLookUpDetails;
	}

	@Override
	public Map<CurrencyPair, Double> getBaseTermCurrencyExchangeRateMap() {
		return this.baseTermCurrencyExchangeRateMap;
	}

	public void setBaseTermCurrencyExchangeRateMap(
			Map<CurrencyPair, Double> baseTermCurrencyExchangeRateMap) {
		this.baseTermCurrencyExchangeRateMap = baseTermCurrencyExchangeRateMap;
	}

}
