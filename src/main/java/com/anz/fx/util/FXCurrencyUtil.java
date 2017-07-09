package com.anz.fx.util;

import java.util.Currency;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FXCurrencyUtil {

	@Autowired
	private CrossCurrencyLookUpLoader crossCurrencyLookUpLoader;
	
	public String validateBaseTermCurrencies(String baseCurrencyCode,String termCurrencyCode){
		
		String validationMessage ="";
		if(baseCurrencyCode!=null && !baseCurrencyCode.isEmpty()){
			if(termCurrencyCode!=null && !termCurrencyCode.isEmpty()){
				
				if(Currency.getAvailableCurrencies().contains(baseCurrencyCode)){
					if(Currency.getAvailableCurrencies().contains(termCurrencyCode)){
						List<Currency> supportedFXCurrenciesList = crossCurrencyLookUpLoader.getSupportedFXCurrenciesList();
						Currency baseCurrency = Currency.getInstance(baseCurrencyCode);
						Currency termCurrency = Currency.getInstance(termCurrencyCode);
						if(supportedFXCurrenciesList.contains(baseCurrency)){
							if(supportedFXCurrenciesList.contains(termCurrency)){
							  //base term  currency all fine
							}else{
								validationMessage+="Term Currency Code provided is not Supported .Please enter a valid Term Currency Code. ";
							}
						}else{
							validationMessage+="Base Currency Code provided is not Supported .Please enter a valid Base Currency Code. ";
						}
						
					}else{
						validationMessage+="Term Currency Code provided is not a valid ISO Currency Code .Please enter a valid Base Currency Code. ";
					}
					
				}else{
					validationMessage+="Base Currency Code provided is not a valid ISO Currency Code .Please enter a valid Base Currency Code. ";
				}
				
			}else{
				validationMessage+="Term Currency Code cannot be empty .Please enter a valid value. ";
			}
		}else{
			validationMessage+="Base Currency Code cannot be empty .Please enter a valid value. ";
		}
		return validationMessage;
	}
}
