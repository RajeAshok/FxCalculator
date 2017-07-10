package com.anz.fx.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;
import com.anz.fx.service.CrossViaCurrencyLookUpService;

@Service
public class CrossViaCurrencyLookUpServiceImpl implements
		CrossViaCurrencyLookUpService {

   private Map<CurrencyPair, String> directIndirectCurrLookUpMap = new HashMap<>();
   
   private Map<String,List<CurrencyPair>> crossViaCurrencyLookUpMap = new HashMap<>();
   
   private List<String> supportedFXCurrenciesList = new ArrayList<>();
	 
	
   @Value("classpath:FXCrossViaLookUp.txt")
	private Resource crossVialookUpResource;
   
	@Override
	public void loadCrossViaCurrencyLookUpFromFile() {
		
		try {
			List<String> baseTermCurrencyLookUpDetails = Files.readAllLines(
					Paths.get(crossVialookUpResource.getURI()), StandardCharsets.UTF_8);
			System.out.println("Input number of lines..."
					+ baseTermCurrencyLookUpDetails.size());
			
			System.out.println("baseTermCurrencyLookUpDetails first item"
					+ baseTermCurrencyLookUpDetails.get(0));
			
			String[] termCurrencyElements = baseTermCurrencyLookUpDetails.get(0).split("\\s+");
			List<String> termCurrencyList = new ArrayList<String>(Arrays.asList(termCurrencyElements));
			//termcurrencyList- [AUD, CAD, CNY, CZK, DKK, EUR, GBP, JPY, NOK, NZD, USD]
			
			List<CurrencyPair> viaEURCurrencyPairList = new ArrayList<>();
			List<CurrencyPair> viaUSDCurrencyPairList = new ArrayList<>();
			
			System.out.println("baseTermCurrencyLookUpDetails.size().." +baseTermCurrencyLookUpDetails.size());
			
			for (int i = 1; i < baseTermCurrencyLookUpDetails.size(); i++) {
				String baseCurrencyLookUpString =baseTermCurrencyLookUpDetails.get(i);
				//baseCurrencyLookUpString- AUD 1:1 USD USD USD USD USD USD USD USD USD D
				String[] baseCurrencyLookUpTypeElements = baseCurrencyLookUpString.split("\\s+");
				List<String> baseCurrencyLookUpTypeList = new ArrayList<String>(Arrays.asList(baseCurrencyLookUpTypeElements));
				String baseCurrencyKey = baseCurrencyLookUpTypeList.get(0);
				supportedFXCurrenciesList.add(baseCurrencyKey);
				for(int j=1;j< baseCurrencyLookUpTypeList.size() ;j++){
					String termCurrencyKey =termCurrencyList.get(j);
					CurrencyPair baseTermCurrencyPair = new CurrencyPair(baseCurrencyKey,termCurrencyKey);
					String baseCurrencyLookUptype = baseCurrencyLookUpTypeList.get(j);
				    if(baseCurrencyLookUptype.equalsIgnoreCase("D") ){
				    	
				    	directIndirectCurrLookUpMap.put(baseTermCurrencyPair, LookUpType.DIRECT.getValue());
				    	baseTermCurrencyPair= new CurrencyPair(termCurrencyKey, baseCurrencyKey);
				    	directIndirectCurrLookUpMap.put(baseTermCurrencyPair, LookUpType.INVERSE.getValue());
				    }else if(baseCurrencyLookUptype.equalsIgnoreCase("Inv")){
				    	directIndirectCurrLookUpMap.put(baseTermCurrencyPair, LookUpType.INVERSE.getValue());
				    	baseTermCurrencyPair= new CurrencyPair(termCurrencyKey, baseCurrencyKey);
				    	directIndirectCurrLookUpMap.put(baseTermCurrencyPair, LookUpType.DIRECT.getValue());
				    }else if(baseCurrencyLookUptype.equalsIgnoreCase(LookUpType.VIAEUR.getValue())){
				    	 viaEURCurrencyPairList.add(baseTermCurrencyPair);
				    }else if(baseCurrencyLookUptype.equalsIgnoreCase(LookUpType.VIAUSD.getValue())){
				    	 viaUSDCurrencyPairList.add(baseTermCurrencyPair);
				    }
				}
			}
			System.out.println("viaEURCurrencyPairList.." +viaEURCurrencyPairList.size());
			System.out.println("viaUSDCurrencyPairList.." +viaUSDCurrencyPairList.size());
			viaUSDCurrencyPairList.forEach((CurrencyPair) -> System.out.println("usdList item --> "+ CurrencyPair.getBaseCurrKey() + ":"+ CurrencyPair.getTermCurrKey() ));
			System.out.println("directIndirectCurrLookUpMap.." +directIndirectCurrLookUpMap.size());
			setSupportedFXCurrenciesList(supportedFXCurrenciesList);
			System.out.println("supportedFXCurrenciesList.." +supportedFXCurrenciesList.size());
			this.setDirectIndirectCurrLookUpMap(directIndirectCurrLookUpMap);
			
			crossViaCurrencyLookUpMap.put(LookUpType.VIAUSD.getValue(), viaUSDCurrencyPairList);
			crossViaCurrencyLookUpMap.put(LookUpType.VIAEUR.getValue(), viaEURCurrencyPairList);
			this.setCrossViaCurrencyLookUpMap(crossViaCurrencyLookUpMap);

		} catch (IOException ex) {
			System.out.println(" reader cound not be initialized. ");
		}

	}
	
	@Override
	public Map<CurrencyPair, String> fetchDirectIndirectCurrLookUpMap() {
		return this.getDirectIndirectCurrLookUpMap();
	}
	
	@Override
	public Map<String, List<CurrencyPair>> fetchCrossViaCurrencyLookUpMap() {
		return this.getCrossViaCurrencyLookUpMap();
	}

	public Map<CurrencyPair, String> getDirectIndirectCurrLookUpMap() {
		return this.directIndirectCurrLookUpMap;
	}

	public void setDirectIndirectCurrLookUpMap(
			Map<CurrencyPair, String> directIndirectCurrLookUpMap) {
		this.directIndirectCurrLookUpMap = directIndirectCurrLookUpMap;
	}

	public Map<String, List<CurrencyPair>> getCrossViaCurrencyLookUpMap() {
		return crossViaCurrencyLookUpMap;
	}

	public void setCrossViaCurrencyLookUpMap(
			Map<String, List<CurrencyPair>> crossViaCurrencyLookUpMap) {
		this.crossViaCurrencyLookUpMap = crossViaCurrencyLookUpMap;
	}

	public List<String> getSupportedFXCurrenciesList() {
		return supportedFXCurrenciesList;
	}

	public void setSupportedFXCurrenciesList(List<String> supportedFXCurrenciesList) {
		this.supportedFXCurrenciesList = supportedFXCurrenciesList;
	}

	@Override
	public List<String> fetchSupportedFXCurrenciesList() {
		// TODO Auto-generated method stub
		return getSupportedFXCurrenciesList();
	}

}
