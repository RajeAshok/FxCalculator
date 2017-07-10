package com.anz.fx.service;

import java.util.List;
import java.util.Map;

import com.anz.fx.model.CurrencyPair;

public interface CrossViaCurrencyLookUpService {

	 void loadCrossViaCurrencyLookUpFromFile();
	 
	 Map<CurrencyPair,String> fetchDirectIndirectCurrLookUpMap();
	 
	 Map<String,List<CurrencyPair>> fetchCrossViaCurrencyLookUpMap();
	 
	 List<String> fetchSupportedFXCurrenciesList();
	 
	 
}
