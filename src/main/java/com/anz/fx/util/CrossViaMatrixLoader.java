package com.anz.fx.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import com.anz.fx.model.LookUpType;


@Component
public class CrossViaMatrixLoader {

	private Map<String,Map<String,String>> currencyPairLookUpMap
	= new HashMap<>();
	
	private Map<String,Map<String,String>> viaCurrLookUpMap
	= new HashMap<>();

	public  Map<String,Map<String,String>> loadCrcyRelationShipMap(){

		HashMap<String,String> audRelationMap = new HashMap<>();
		audRelationMap.put("AUD",LookUpType.UNITY.getValue());
		audRelationMap.put("CAD",LookUpType.VIAUSD.getValue());
		audRelationMap.put("CNY",LookUpType.VIAUSD.getValue());
		audRelationMap.put("CZK",LookUpType.VIAUSD.getValue());
		audRelationMap.put("DKK",LookUpType.VIAUSD.getValue());
		audRelationMap.put("EUR",LookUpType.VIAUSD.getValue());
		audRelationMap.put("GBP",LookUpType.VIAUSD.getValue());
		audRelationMap.put("JPY",LookUpType.VIAUSD.getValue());
		audRelationMap.put("NOK",LookUpType.VIAUSD.getValue());
		audRelationMap.put("NZD",LookUpType.VIAUSD.getValue());
		audRelationMap.put("USD",LookUpType.DIRECT.getValue());


		HashMap<String,String> cadRelationMap = new HashMap<String,String>();
		cadRelationMap.put("AUD",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("CAD",LookUpType.UNITY.getValue());
		cadRelationMap.put("CNY",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("CZK",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("DKK",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("EUR",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("GBP",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("JPY",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("NOK",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("NZD",LookUpType.VIAUSD.getValue());
		cadRelationMap.put("USD",LookUpType.DIRECT.getValue());


		HashMap<String,String> eurRelationMap = new HashMap<String,String>();
		eurRelationMap.put("AUD",LookUpType.VIAUSD.getValue());
		eurRelationMap.put("CAD",LookUpType.VIAUSD.getValue());
		eurRelationMap.put("CNY",LookUpType.VIAUSD.getValue());
		eurRelationMap.put("CZK",LookUpType.DIRECT.getValue());
		eurRelationMap.put("DKK",LookUpType.DIRECT.getValue());
		eurRelationMap.put("EUR",LookUpType.UNITY.getValue());
		eurRelationMap.put("GBP",LookUpType.VIAUSD.getValue());
		eurRelationMap.put("JPY",LookUpType.VIAUSD.getValue());
		eurRelationMap.put("NOK",LookUpType.DIRECT.getValue());
		eurRelationMap.put("NZD",LookUpType.VIAUSD.getValue());
		eurRelationMap.put("USD",LookUpType.DIRECT.getValue());

		Map<String,String> usdRelationMap = new HashMap<String,String>();
		usdRelationMap.put("AUD",LookUpType.INVERSE.getValue());
		usdRelationMap.put("CAD",LookUpType.INVERSE.getValue());
		usdRelationMap.put("CNY",LookUpType.DIRECT.getValue()); //requirement doc given wrongly check CNY USD also CNY/USD-INV
		usdRelationMap.put("CZK",LookUpType.VIAEUR.getValue());
		usdRelationMap.put("DKK",LookUpType.VIAEUR.getValue());
		usdRelationMap.put("EUR",LookUpType.INVERSE.getValue());
		usdRelationMap.put("GBP",LookUpType.INVERSE.getValue());
		usdRelationMap.put("JPY",LookUpType.DIRECT.getValue());
		usdRelationMap.put("NOK",LookUpType.VIAEUR.getValue());
		usdRelationMap.put("NZD",LookUpType.INVERSE.getValue());
		usdRelationMap.put("USD",LookUpType.UNITY.getValue());

		

		currencyPairLookUpMap.put("AUD",audRelationMap);
		currencyPairLookUpMap.put("CAD",cadRelationMap);
		currencyPairLookUpMap.put("EUR",eurRelationMap);
		currencyPairLookUpMap.put("USD",usdRelationMap);

		System.out.println("currRelLookUpMap map... " +currencyPairLookUpMap.size() );
		System.out.println("currRelLookUpMap map... " +currencyPairLookUpMap.toString() );

		return currencyPairLookUpMap;
	}

}
