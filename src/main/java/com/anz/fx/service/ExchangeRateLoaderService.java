package com.anz.fx.service;

import java.util.Map;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.model.CurrencyPair;


public interface ExchangeRateLoaderService {
	/**
	 * Loads exchange rates for a currency pair from a flat file
	 * @throws FXDetailValidationException
	 */
	void loadBaseTermCurrencyExchangeRates() throws FXDetailValidationException;
	
	Map<CurrencyPair, Double> getBaseTermCurrencyExchangeRateMap();
	
}

