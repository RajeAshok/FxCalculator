package com.anz.fx.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.anz.fx.model.CurrencyPair;


@Component
public class ExchangeRateLoader {

	private  Map<CurrencyPair,Double> currExchangeRateMap = new HashMap<>();
	
	public Map<CurrencyPair,Double> loadExchangeRates(){
		
		CurrencyPair  audUsd = new CurrencyPair("AUD", "USD");
		CurrencyPair  cadUsd = new CurrencyPair("CAD", "USD");
		CurrencyPair  usdCny = new CurrencyPair("USD", "CNY");
		CurrencyPair  eurUsd = new CurrencyPair("EUR", "USD");
		CurrencyPair  gbpUsd = new CurrencyPair("GBP", "USD");
		CurrencyPair  nzdUsd = new CurrencyPair("NZD", "USD");
		CurrencyPair  usdJpy = new CurrencyPair("USD", "JPY");
		CurrencyPair  eurCzk = new CurrencyPair("EUR", "CZK");
		CurrencyPair  eurDkk = new CurrencyPair("EUR", "DKK");
		CurrencyPair  eurNok = new CurrencyPair("EUR", "NOK");
		
		
		currExchangeRateMap.put(audUsd, 0.8371);
		currExchangeRateMap.put(cadUsd, 0.8711);
		currExchangeRateMap.put(usdCny, 6.1715);
		currExchangeRateMap.put(eurUsd, 1.2315);
		currExchangeRateMap.put(gbpUsd, 1.5683);
		currExchangeRateMap.put(nzdUsd, 0.7750);
		currExchangeRateMap.put(usdJpy, 119.95);
		currExchangeRateMap.put(eurCzk, 27.6028);
		currExchangeRateMap.put(eurDkk, 7.4405);
		currExchangeRateMap.put(eurNok, 8.6651);
		
		return currExchangeRateMap;
	}
}
