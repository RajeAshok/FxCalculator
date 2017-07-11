package com.anz.fx.service;

import java.util.Map;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.model.CurrencyPair;

public interface ExchangeRateLoaderService {
	
	void loadBaseTermCurrencyExchangeRates() throws FXDetailValidationException;
	
	Map<CurrencyPair,Double> fetchBaseCurrencyExchangeRateMap();

}

