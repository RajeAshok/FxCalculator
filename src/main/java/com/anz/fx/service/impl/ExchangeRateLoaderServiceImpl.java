package com.anz.fx.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.anz.fx.model.CurrencyPair;
import com.anz.fx.service.ExchangeRateLoaderService;

@Service
public class ExchangeRateLoaderServiceImpl implements ExchangeRateLoaderService {

	 private Map<CurrencyPair,Double> baseTermCurrencyExchangeRateMap =new HashMap<>();
	 
	 @Value("classpath:BaseTermCurrencyExchangeRates.txt")
		private  Resource exchangeRateLookUpResource;
	
	@Override
	public void loadBaseTermCurrencyExchangeRates() {
		
		try{
		List<String> baseTermCurrencyExchangRateLookUpDetails = Files.readAllLines(
				Paths.get(exchangeRateLookUpResource.getURI()), StandardCharsets.UTF_8);
		System.out.println("baseTermCurrencyExchangRateLookUpDetails.size().." + baseTermCurrencyExchangRateLookUpDetails.size());
		for (int i = 0; i < baseTermCurrencyExchangRateLookUpDetails.size(); i++) {
			String baseTermCurrencyExchRateString =baseTermCurrencyExchangRateLookUpDetails.get(i);
			System.out.println("baseTermCurrencyExchRateString.. " +baseTermCurrencyExchRateString);
			String[] baseCurrencyExchangeRateElements = baseTermCurrencyExchRateString.split("\\s+");
			System.out.println("baseCurrencyExchangeRateElements.length.. " +baseCurrencyExchangeRateElements.length);
			int baseTermExchangeRateRecordLength = baseCurrencyExchangeRateElements.length;
		
			String baseCurrencyCode = baseCurrencyExchangeRateElements[baseTermExchangeRateRecordLength-baseTermExchangeRateRecordLength];
			String termCurrencyCode = baseCurrencyExchangeRateElements[baseTermExchangeRateRecordLength -(baseTermExchangeRateRecordLength-1)];
			String exchangeRate = baseCurrencyExchangeRateElements[baseTermExchangeRateRecordLength-1];
			//Add the currencyPair in inverted order to have all mappings
			baseTermCurrencyExchangeRateMap.put(new CurrencyPair(baseCurrencyCode,termCurrencyCode), Double.parseDouble(exchangeRate));
		
		}		
		setBaseTermCurrencyExchangeRateMap(baseTermCurrencyExchangeRateMap);
		}catch(IOException ex){
			System.out.println("IOEXception ");
		}
	}

	@Override
	public Map<CurrencyPair, Double> fetchBaseCurrencyExchangeRateMap() {
		// TODO Auto-generated method stub
		System.out.println("Size of Map " + getBaseTermCurrencyExchangeRateMap().size());
		return getBaseTermCurrencyExchangeRateMap();
	}

	public Map<CurrencyPair, Double> getBaseTermCurrencyExchangeRateMap() {
		return baseTermCurrencyExchangeRateMap;
	}

	public void setBaseTermCurrencyExchangeRateMap(
			Map<CurrencyPair, Double> baseTermCurrencyExchangeRateMap) {
		this.baseTermCurrencyExchangeRateMap = baseTermCurrencyExchangeRateMap;
	}

}
