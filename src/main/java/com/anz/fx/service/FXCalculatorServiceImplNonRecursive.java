/*package com.anz.fx.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anz.fx.exception.UnSupportedCurrencyException;
import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;
import com.anz.fx.util.CrossViaMatrixLoader;
import com.anz.fx.util.ExchangeRateLoader;

@Service
public class FXCalculatorServiceImplNonRecursive implements FXCalculatorService{
    
	private static Map<String,Map<String,String>> currencyRelLookUp = null;
	
	@Autowired
	private CrossViaMatrixLoader crossViaMatrixLoader;
	
	@Autowired
	private ExchangeRateLoader exchangeRateLoader;
		
	@Override
	public BigDecimal calculateFXAmount(String baseCurrencyCode,
			BigDecimal baseCurrAmt, String termCurrencyCode)
			throws UnSupportedCurrencyException {
		// TODO Auto-generated method stub
		
		Currency baseCurrency = Currency.getInstance(baseCurrencyCode);
		Currency termCurrency= Currency.getInstance(termCurrencyCode);
		System.out.println("FXCurrencyInfo created...");
		String baseToTermRelation =fetchInputCurrenciesRelation(baseCurrency,termCurrency);	
		return calculateTermCurrAmount(baseCurrencyCode,termCurrencyCode,baseToTermRelation,baseCurrAmt);
	}
	
	private  String fetchInputCurrenciesRelation(Currency baseCurrency,Currency termCurrency){

		String baseCurrCode = baseCurrency.getCurrencyCode();
		String termCurrCode = termCurrency.getCurrencyCode();
        System.out.println("baseCurrCode..." +baseCurrCode);
        System.out.println("termCurrCode..." +termCurrCode);
        
        currencyRelLookUp = crossViaMatrixLoader.loadCrcyRelationShipMap();

		HashMap<String,String> baseCurrRelMap = (HashMap<String,String>) currencyRelLookUp.get(baseCurrCode);
		System.out.println("baseCurrRelMap..." +baseCurrRelMap.size()); 

		String currencyRel = baseCurrRelMap.get(termCurrCode);
		System.out.println("currencyRel..." +currencyRel);
		return  currencyRel;
	}

	private  BigDecimal calculateTermCurrAmount(String baseCurrencyCode,String termCurrencyCode ,String lookUpType,BigDecimal baseCurrAmt){
		
		Map<CurrencyPair,Double> exchangeRateMap =exchangeRateLoader.loadExchangeRates();
		System.out.println("Exchange rate map size"+ exchangeRateMap.size() + "" + exchangeRateMap.values());
		double exchangeRate = 0.00;
		BigDecimal termCurrAmount = null;
		CurrencyPair currencyPair = new  CurrencyPair(baseCurrencyCode,termCurrencyCode);
		Currency baseCurrency = Currency.getInstance(baseCurrencyCode);
		Currency termCurrency= Currency.getInstance(termCurrencyCode);
		System.out.println("currencyPair .."+currencyPair.toString());
		if(lookUpType.equals(LookUpType.DIRECT.getValue())){
			
			System.out.println("LookUp type is direct");
		
			exchangeRate = exchangeRateMap.get(currencyPair);
			System.out.println("Direct exchange rate..." + exchangeRate);
			
			termCurrAmount = baseCurrAmt.multiply(new BigDecimal(exchangeRate));
			System.out.println("termCurrAmount.." +termCurrAmount);
			
		}else if(lookUpType.equals(LookUpType.INVERSE.getValue())){
			System.out.println("LookUp type is Inverse");
			exchangeRate = exchangeRateMap.get(new CurrencyPair(termCurrency.getCurrencyCode(),baseCurrency.getCurrencyCode()));
			double invExchRangeRate = 1/exchangeRate;
			termCurrAmount = baseCurrAmt.multiply(new BigDecimal(invExchRangeRate));
			termCurrAmount =termCurrAmount.setScale(termCurrency.getDefaultFractionDigits(), RoundingMode.UP);
			System.out.println("termCurrAmount.." +termCurrAmount);
		}else if(lookUpType.equals(LookUpType.VIAUSD.getValue())){
			System.out.println("LookUp type is " +  LookUpType.VIAUSD.getValue());
			termCurrAmount= computeAmtforViaCurrencies(baseCurrency,termCurrency,baseCurrAmt,exchangeRateMap,lookUpType);
			System.out.println("termCurrAmount..." +termCurrAmount);
			String baseUSDCurrRel = fetchInputCurrenciesRelation(baseCurrency, Currency.getInstance(LookUpType.VIAUSD.getValue()));
			System.out.println("baseViaUSD..." + baseUSDCurrRel);
			double baseUSDPairExchRate;
			BigDecimal baseUSDAmount= null;
			if(baseUSDCurrRel.equals(LookUpType.INVERSE.getValue())){
				exchangeRate=exchangeRateMap.get(new CurrencyPair(LookUpType.VIAUSD.getValue(),baseCurrency.getCurrencyCode()));
				baseUSDPairExchRate= 1/exchangeRate ;
			}else{
				baseUSDPairExchRate=exchangeRateMap.get(new CurrencyPair(baseCurrency.getCurrencyCode(),LookUpType.VIAUSD.getValue()));
				
			}
			baseUSDAmount = baseCurrAmt.multiply(new BigDecimal(baseUSDPairExchRate));
			
			String usdTermCurrRel =fetchInputCurrenciesRelation(Currency.getInstance(LookUpType.VIAUSD.getValue()),termCurrency);
			System.out.println("usdTermCurrRel..." + usdTermCurrRel);
			double usdTermPairExchRate;
			BigDecimal usdTermAmount =null;
			if(usdTermCurrRel.equals(LookUpType.INVERSE.getValue())){
				exchangeRate=exchangeRateMap.get(new CurrencyPair(termCurrency.getCurrencyCode(),LookUpType.VIAUSD.getValue()));
				usdTermPairExchRate= 1/exchangeRate ;
			}else{
				usdTermPairExchRate=exchangeRateMap.get(new CurrencyPair(LookUpType.VIAUSD.getValue(),termCurrency.getCurrencyCode()));
				
			}
			termCurrAmount = baseUSDAmount.multiply(new BigDecimal(usdTermPairExchRate));
			System.out.println("termCurrAmount..." +termCurrAmount);
			
		}else if(lookUpType.equals(LookUpType.VIAEUR.getValue())){
			System.out.println("LookUp type is " +  LookUpType.VIAEUR.getValue());
			termCurrAmount= computeAmtforViaCurrencies(baseCurrency,termCurrency,baseCurrAmt,exchangeRateMap,lookUpType);
			System.out.println("termCurrAmount..." +termCurrAmount);
			String baseEurCurrRel = fetchInputCurrenciesRelation(baseCurrency, Currency.getInstance(LookUpType.VIAEUR.getValue()));
			System.out.println("baseEurCurrRel..." + baseEurCurrRel);
			double baseEURPairExchRate;
			BigDecimal baseEURAmount= null;
			if(baseEurCurrRel.equals(LookUpType.INVERSE.getValue())){
				exchangeRate=exchangeRateMap.get(new CurrencyPair(LookUpType.VIAEUR.getValue(),baseCurrency.getCurrencyCode()));
				baseEURPairExchRate= 1/exchangeRate ;
			}else{
				baseEURPairExchRate=exchangeRateMap.get(new CurrencyPair(baseCurrency.getCurrencyCode(),LookUpType.VIAEUR.getValue()));
				
			}
			baseEURAmount = baseCurrAmt.multiply(new BigDecimal(baseEURPairExchRate));
			
			String eurTermCurrRel =fetchInputCurrenciesRelation(Currency.getInstance(LookUpType.VIAEUR.getValue()),termCurrency);
			System.out.println("eurTermCurrRel..." + eurTermCurrRel);
			double eurTermPairExchRate;
			if(eurTermCurrRel.equals(LookUpType.INVERSE.getValue())){
				exchangeRate=exchangeRateMap.get(new CurrencyPair(termCurrency.getCurrencyCode(),LookUpType.VIAEUR.getValue()));
				eurTermPairExchRate= 1/exchangeRate ;
			}else{
				eurTermPairExchRate=exchangeRateMap.get(new CurrencyPair(LookUpType.VIAEUR.getValue(),termCurrency.getCurrencyCode()));
				
			}
			termCurrAmount = baseEURAmount.multiply(new BigDecimal(eurTermPairExchRate));
			System.out.println("termCurrAmount..." +termCurrAmount);
		}
		return formatConvertedAmount(termCurrAmount,termCurrency) ;
		
	}
	
	private BigDecimal computeAmtforViaCurrencies(Currency baseCurrency, Currency termCurrency,
			BigDecimal baseCurrAmt ,Map<CurrencyPair,Double> exchangeRateMap ,String lookUpType){
		System.out.println("LookUp type is " +  lookUpType);
		String baseViaCurrPairRel = fetchInputCurrenciesRelation(baseCurrency, Currency.getInstance(lookUpType));
		System.out.println("baseViaCurrPairRel..." + baseViaCurrPairRel);
		double baseViaCurrPairExchRate;
		BigDecimal baseToViaCurrAmount= null;
		BigDecimal convertedCurrAmount= null;
		if(baseViaCurrPairRel.equals(LookUpType.INVERSE.getValue())){
			baseViaCurrPairExchRate= 1/(exchangeRateMap.get(new CurrencyPair(lookUpType,baseCurrency.getCurrencyCode() ))) ;
		}else{
			baseViaCurrPairExchRate=exchangeRateMap.get(new CurrencyPair(baseCurrency.getCurrencyCode(),lookUpType ));
			
		}
		baseToViaCurrAmount = baseCurrAmt.multiply(new BigDecimal(baseViaCurrPairExchRate));
		
		String viaTermCurrPairRel =fetchInputCurrenciesRelation(Currency.getInstance(lookUpType),termCurrency);
		System.out.println("viaTermCurrPairRel..." + viaTermCurrPairRel);
		double viaTermCurrPairExchRate;
		if(viaTermCurrPairRel.equals(LookUpType.INVERSE.getValue())){
			viaTermCurrPairExchRate= 1/(exchangeRateMap.get(new CurrencyPair(termCurrency.getCurrencyCode(),lookUpType))) ;
		}else{
			viaTermCurrPairExchRate=exchangeRateMap.get(new CurrencyPair(lookUpType,termCurrency.getCurrencyCode()));
			
		}
		convertedCurrAmount = baseToViaCurrAmount.multiply(new BigDecimal(viaTermCurrPairExchRate));
		System.out.println("convertedCurrAmount..." +convertedCurrAmount);
		
		return convertedCurrAmount;
	}
	
	*//**
	 * Apply format to term currency , setting the precision as per the ISO standard for currency codes
	 * @param calculatedAmount
	 * @param termCurrency
	 * @return
	 *//*
	private BigDecimal formatConvertedAmount(BigDecimal calculatedAmount, Currency termCurrency){
		return calculatedAmount.setScale(termCurrency.getDefaultFractionDigits(), RoundingMode.UP);
		
	}
	
}
*/