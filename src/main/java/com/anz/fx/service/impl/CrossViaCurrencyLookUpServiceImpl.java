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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;
import com.anz.fx.service.CrossViaCurrencyLookUpService;

@Service
public class CrossViaCurrencyLookUpServiceImpl implements
		CrossViaCurrencyLookUpService {
	
   private static final Logger LOG = LoggerFactory.getLogger(CrossViaCurrencyLookUpServiceImpl.class);	

   private Map<CurrencyPair, String> directIndirectCurrLookUpMap = new HashMap<>();
   
   private Map<String,List<CurrencyPair>> crossViaCurrencyLookUpMap = new HashMap<>();
   
   private List<Currency> supportedFXCurrenciesList = new ArrayList<>();
	 
	
    @Value("classpath:FXCrossViaLookUp.txt")
	private Resource crossVialookUpResource;
   
	@Override
	public void loadCrossViaCurrencyLookUpFromFile() throws FXDetailValidationException {
		
		    LOG.debug("loading currency pair details from flat file");
			List<String> baseTermCurrencyLookUpDetails = loadResourceFile();
			
			String[] termCurrencyElements = baseTermCurrencyLookUpDetails.get(0).split("\\s+");
			List<String> termCurrencyList = new ArrayList<String>(Arrays.asList(termCurrencyElements));
			//termcurrencyList- [AUD, CAD, CNY, CZK, DKK, EUR, GBP, JPY, NOK, NZD, USD]
			
			List<CurrencyPair> viaEURCurrencyPairList = new ArrayList<>();
			List<CurrencyPair> viaUSDCurrencyPairList = new ArrayList<>();
			;
			
			for (int i = 1; i < baseTermCurrencyLookUpDetails.size(); i++) {
				String baseCurrencyLookUpString =baseTermCurrencyLookUpDetails.get(i);
				String[] baseCurrencyLookUpTypeElements = baseCurrencyLookUpString.split("\\s+");
				List<String> baseCurrencyLookUpTypeList = new ArrayList<String>(Arrays.asList(baseCurrencyLookUpTypeElements));
				String baseCurrencyKey = baseCurrencyLookUpTypeList.get(0);
				supportedFXCurrenciesList.add(Currency.getInstance(baseCurrencyKey));
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

			setSupportedFXCurrenciesList(supportedFXCurrenciesList);
			this.setDirectIndirectCurrLookUpMap(directIndirectCurrLookUpMap);
			
			crossViaCurrencyLookUpMap.put(LookUpType.VIAUSD.getValue(), viaUSDCurrencyPairList);
			crossViaCurrencyLookUpMap.put(LookUpType.VIAEUR.getValue(), viaEURCurrencyPairList);
			this.setCrossViaCurrencyLookUpMap(crossViaCurrencyLookUpMap);
	}
	
	protected List<String> loadResourceFile() throws FXDetailValidationException{
		try {
			List<String> baseTermCurrencyLookUpDetails = Files.readAllLines(
					Paths.get(crossVialookUpResource.getURI()), StandardCharsets.UTF_8);
			return baseTermCurrencyLookUpDetails;
		} catch (IOException e) {
			throw new FXDetailValidationException("Error loading he input Resource file");
		}
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

	public List<Currency> getSupportedFXCurrenciesList() {
		return supportedFXCurrenciesList;
	}

	public void setSupportedFXCurrenciesList(
			List<Currency> supportedFXCurrenciesList) {
		this.supportedFXCurrenciesList = supportedFXCurrenciesList;
	}

}
