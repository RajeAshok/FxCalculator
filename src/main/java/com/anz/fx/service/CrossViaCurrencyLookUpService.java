package com.anz.fx.service;

import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;

import com.anz.fx.model.CurrencyPair;

public interface CrossViaCurrencyLookUpService {

	 void loadCrossViaCurrencyLookUpFromFile();
	 
	 Map<CurrencyPair,String> fetchDirectIndirectCurrLookUpMap();
	 
	 Map<String,List<CurrencyPair>> fetchCrossViaCurrencyLookUpMap();
	 
	 List<Currency> fetchSupportedFXCurrenciesList();
	 
	 
}
