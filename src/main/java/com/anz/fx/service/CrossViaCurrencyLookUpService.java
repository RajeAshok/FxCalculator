package com.anz.fx.service;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.model.CurrencyPair;

public interface CrossViaCurrencyLookUpService {
	/**
	 * Loads currency pair look-up relations
	 * @throws FXDetailValidationException
	 */
	 void loadCrossViaCurrencyLookUpFromFile() throws FXDetailValidationException;
	 
	 /**
	  * Returns a map containing direct/inverse relationship currency pairs
	  * @return
	  */
	 Map<CurrencyPair,String> getDirectIndirectCurrLookUpMap();
	 
	 /**
	  * Returns a map containing currency pairs that have a via route
	  * @return
	  */
	 Map<String,List<CurrencyPair>> getCrossViaCurrencyLookUpMap();
	 
	 /**
	  * Returns a list of supported currencies
	  * @return
	  */
	 List<Currency> getSupportedFXCurrenciesList();
	 
}
