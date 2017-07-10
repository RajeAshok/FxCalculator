package com.anz.fx.util;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;

@Component
public class CrossCurrencyLookUpLoader {
	
	private List<CurrencyPair> viaUSDCurrencyPairList = new ArrayList<>();
	private Map<String,List<CurrencyPair>> crossViaCurrencyLookUpMap = new HashMap<>();
	
	private  Map<CurrencyPair,String> directionLookUpMap=new HashMap<>();
	
	private List<Currency> supportedFXCurrenciesList = new ArrayList<>();
	
	public List<Currency> getSupportedFXCurrenciesList(){
		supportedFXCurrenciesList.add(Currency.getInstance("AUD"));
		supportedFXCurrenciesList.add(Currency.getInstance("CAD"));
		supportedFXCurrenciesList.add(Currency.getInstance("CNY"));
		supportedFXCurrenciesList.add(Currency.getInstance("CZK"));
		supportedFXCurrenciesList.add(Currency.getInstance("DKK"));
		supportedFXCurrenciesList.add(Currency.getInstance("EUR"));
		supportedFXCurrenciesList.add(Currency.getInstance("GBP"));
		supportedFXCurrenciesList.add(Currency.getInstance("JPY"));
		supportedFXCurrenciesList.add(Currency.getInstance("NOK"));
		supportedFXCurrenciesList.add(Currency.getInstance("NZD"));
		supportedFXCurrenciesList.add(Currency.getInstance("USD"));
		
		return supportedFXCurrenciesList;
	}
	
	public  Map<CurrencyPair,String> loadDirectionLookUpMap(){
		CurrencyPair  usdAud = new CurrencyPair("USD", "AUD");
		CurrencyPair  audUsd = new CurrencyPair("AUD", "USD");
		CurrencyPair  usdCad = new CurrencyPair("USD", "CAD");
		CurrencyPair  usdCny = new CurrencyPair("USD", "CNY");
		CurrencyPair  usdEur = new CurrencyPair("USD", "EUR");
		CurrencyPair  usdGbp = new CurrencyPair("USD", "GBP");
		CurrencyPair  gbpUsd = new CurrencyPair("GBP", "USD");
		CurrencyPair  usdJpy = new CurrencyPair("USD", "JPY");
		CurrencyPair  usdNzd = new CurrencyPair("USD", "NZD");
		CurrencyPair  eurCzk = new CurrencyPair("EUR", "CZK");
		CurrencyPair  eurDkk = new CurrencyPair("EUR", "DKK");
		CurrencyPair  eurNok = new CurrencyPair("EUR", "NOK");
		
		
		directionLookUpMap.put(usdAud,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(audUsd,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(usdCad,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(usdCny,LookUpType.DIRECT.getValue()); //requirement doc given wrongly check CNY USD also CNY/USD-INV
		directionLookUpMap.put(usdEur,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(usdGbp,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(gbpUsd,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(usdJpy,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(usdNzd,LookUpType.INVERSE.getValue());
		directionLookUpMap.put(eurCzk,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(eurDkk,LookUpType.DIRECT.getValue());
		directionLookUpMap.put(eurNok,LookUpType.DIRECT.getValue());
		
		
		return directionLookUpMap;
	}
	
	public Map<String,List<CurrencyPair>> loadCrossCurrencyLookUpMap(){
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "CAD"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "CNY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "CZK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "DKK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "EUR"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "GBP"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "NOK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "NZD"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "CNY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "CZK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "DKK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "EUR"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "GBP"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "NOK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "NZD"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("CAD", "AUD"));
	viaUSDCurrencyPairList.add(new CurrencyPair("GBP", "NOK"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("CNY", "CZK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CNY", "DKK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CNY", "EUR"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CNY", "GBP"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CNY", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CNY", "NOK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CNY", "NZD"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("CZK", "GBP"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CZK", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("CZK", "NZD"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("DKK", "GBP"));
	viaUSDCurrencyPairList.add(new CurrencyPair("DKK", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("DKK", "NZD"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("DKK", "GBP"));
	viaUSDCurrencyPairList.add(new CurrencyPair("DKK", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("DKK", "NZD"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("EUR", "GBP"));
	viaUSDCurrencyPairList.add(new CurrencyPair("EUR", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("EUR", "NZD"));
	
	viaUSDCurrencyPairList.add(new CurrencyPair("GBP", "JPY"));
	viaUSDCurrencyPairList.add(new CurrencyPair("GBP", "NOK"));
	viaUSDCurrencyPairList.add(new CurrencyPair("GBP", "NZD"));
	
	List<CurrencyPair> viaEURCurrencyPairList = new ArrayList<>();
	viaEURCurrencyPairList.add(new CurrencyPair("CZK", "DKK"));
	viaEURCurrencyPairList.add(new CurrencyPair("CZK", "NOK"));
	viaEURCurrencyPairList.add(new CurrencyPair("CZK", "USD"));
	viaEURCurrencyPairList.add(new CurrencyPair("DKK", "NOK"));
	viaEURCurrencyPairList.add(new CurrencyPair("DKK", "USD"));
	viaEURCurrencyPairList.add(new CurrencyPair("NOK", "USD"));

	viaEURCurrencyPairList.add(new CurrencyPair("USD", "NOK"));
	
	
	
	crossViaCurrencyLookUpMap.put("USD", viaUSDCurrencyPairList);
	crossViaCurrencyLookUpMap.put("EUR", viaEURCurrencyPairList);
	
	return crossViaCurrencyLookUpMap;
	
	}

}
