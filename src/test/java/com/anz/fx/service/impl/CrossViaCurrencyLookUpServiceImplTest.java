package com.anz.fx.service.impl;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;

public class CrossViaCurrencyLookUpServiceImplTest {

	@Autowired
	private CrossViaCurrencyLookUpServiceImpl crossViaCurrencyLookUpServiceImpl;
	
	private Map<CurrencyPair, String> directIndirectCurrLookUpMap = new HashMap<>();
	   
	private Map<String,List<CurrencyPair>> crossViaCurrencyLookUpMap = new HashMap<>();
	   
	private List<Currency> supportedFXCurrenciesList = new ArrayList<>();;
	
	@Value("classpath:FXCrossViaLookUp.txt")
	private Resource crossVialookUpResource;
	
	@Before
	public void setUp() throws FXDetailValidationException{
		
		crossViaCurrencyLookUpServiceImpl = Mockito.spy(new CrossViaCurrencyLookUpServiceImpl());
		Whitebox.setInternalState(crossViaCurrencyLookUpServiceImpl, "crossVialookUpResource", crossVialookUpResource);
		Whitebox.setInternalState(crossViaCurrencyLookUpServiceImpl, "directIndirectCurrLookUpMap", directIndirectCurrLookUpMap);
		Whitebox.setInternalState(crossViaCurrencyLookUpServiceImpl, "crossViaCurrencyLookUpMap", crossViaCurrencyLookUpMap);
		Whitebox.setInternalState(crossViaCurrencyLookUpServiceImpl, "supportedFXCurrenciesList", supportedFXCurrenciesList);
		List<String> resourceFileStringList = new ArrayList<>();
		resourceFileStringList.add("/   AUD CAD CNY CZK DKK EUR GBP JPY NOK NZD USD");
		resourceFileStringList.add("AUD 1:1 USD USD USD USD USD USD USD USD USD D");
		resourceFileStringList.add("EUR USD USD USD D   D   1:1 USD USD D   USD D");
		resourceFileStringList.add("USD Inv Inv D   EUR EUR Inv Inv D   EUR Inv 1:1");
		
		supportedFXCurrenciesList.add(Currency.getInstance("AUD"));
		supportedFXCurrenciesList.add(Currency.getInstance("EUR"));
		supportedFXCurrenciesList.add(Currency.getInstance("USD"));
		
		CurrencyPair  usdAud = new CurrencyPair("USD", "AUD");
		CurrencyPair  audUsd = new CurrencyPair("AUD", "USD");
		CurrencyPair  usdCad = new CurrencyPair("USD", "CAD");
		CurrencyPair  usdCny = new CurrencyPair("USD", "CNY");
		CurrencyPair  usdEur = new CurrencyPair("USD", "EUR");
		CurrencyPair  usdGbp = new CurrencyPair("USD", "GBP");
		CurrencyPair  usdJpy = new CurrencyPair("USD", "JPY");
		CurrencyPair  usdNzd = new CurrencyPair("USD", "NZD");
		CurrencyPair  eurUsd = new CurrencyPair("EUR", "USD");
		CurrencyPair  eurCzk = new CurrencyPair("EUR", "CZK");
		CurrencyPair  eurDkk = new CurrencyPair("EUR", "DKK");
		CurrencyPair  eurNok = new CurrencyPair("EUR", "NOK");
		
		directIndirectCurrLookUpMap.put(audUsd,LookUpType.DIRECT.getValue());
		directIndirectCurrLookUpMap.put(usdAud,LookUpType.INVERSE.getValue());
		directIndirectCurrLookUpMap.put(usdCad,LookUpType.INVERSE.getValue());
		directIndirectCurrLookUpMap.put(usdCny,LookUpType.DIRECT.getValue()); //requirement doc given wrongly check CNY USD also CNY/USD-INV
		directIndirectCurrLookUpMap.put(usdEur,LookUpType.INVERSE.getValue());
		directIndirectCurrLookUpMap.put(usdGbp,LookUpType.INVERSE.getValue());
		directIndirectCurrLookUpMap.put(usdJpy,LookUpType.DIRECT.getValue());
		directIndirectCurrLookUpMap.put(usdNzd,LookUpType.INVERSE.getValue());
		directIndirectCurrLookUpMap.put(eurCzk,LookUpType.DIRECT.getValue());
		directIndirectCurrLookUpMap.put(eurDkk,LookUpType.DIRECT.getValue());
		directIndirectCurrLookUpMap.put(eurNok,LookUpType.DIRECT.getValue());
		directIndirectCurrLookUpMap.put(eurUsd,LookUpType.DIRECT.getValue());
		
		List<CurrencyPair> viaUSDCurrencyPairList = new ArrayList<>();
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "CAD"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "CNY"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "CZK"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "DKK"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "EUR"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "GBP"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "JPY"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "NOK"));
		viaUSDCurrencyPairList.add(new CurrencyPair("AUD", "NZD"));
		viaUSDCurrencyPairList.add(new CurrencyPair("EUR", "GBP"));
		viaUSDCurrencyPairList.add(new CurrencyPair("EUR", "JPY"));
		viaUSDCurrencyPairList.add(new CurrencyPair("EUR", "NZD"));
		
		
		List<CurrencyPair> viaEURCurrencyPairList = new ArrayList<>();
		
		viaEURCurrencyPairList.add(new CurrencyPair("USD", "CZK"));
		viaEURCurrencyPairList.add(new CurrencyPair("USD", "DKK"));
		viaEURCurrencyPairList.add(new CurrencyPair("USD", "NOK"));
		
		crossViaCurrencyLookUpMap.put(LookUpType.VIAUSD.getValue(), viaUSDCurrencyPairList);
		crossViaCurrencyLookUpMap.put(LookUpType.VIAEUR.getValue(), viaEURCurrencyPairList);

		Mockito.doReturn(resourceFileStringList).when(crossViaCurrencyLookUpServiceImpl).loadResourceFile();
	}

	@Test
	public void loadCrossViaCurrencyLookUpFromFileTest() throws FXDetailValidationException{
		crossViaCurrencyLookUpServiceImpl.loadCrossViaCurrencyLookUpFromFile();
		assertEquals(2,crossViaCurrencyLookUpMap.size());
	}
	

}
