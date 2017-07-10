package com.anz.fx.service;

import java.util.Map;

import org.springframework.core.io.Resource;

import com.anz.fx.model.CurrencyPair;

public interface ExchangeRateLoaderService {
	
	void loadBaseTermCurrencyExchangeRates();
	
	Map<CurrencyPair,Double> fetchBaseCurrencyExchangeRateMap();

}

