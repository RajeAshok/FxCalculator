package com.anz.fx.service;

import java.util.Map;

import com.anz.fx.model.CurrencyPair;

public interface ExchangeRateLoaderService {
	
	void loadBaseTermCurrencyExchangeRates();
	
	Map<CurrencyPair,Double> fetchBaseCurrencyExchangeRateMap();

}

