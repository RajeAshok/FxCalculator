package com.anz.fx.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;

public class Localtesting {

	private static Map<CurrencyPair,String> directionLookUpMap=new HashMap<>();
	private static Map<CurrencyPair,Double> exchRateMap = new ExchangeRateLoader().loadExchangeRates();
	
	private static List<CurrencyPair> viaUSDCurrencyPairList = new ArrayList<>();
	private static Map<String,List<CurrencyPair>> crossViaLookUpMap
    = new HashMap<>();
	
	public static void main(String[] args) {
		
		System.out.println("currencies.. \n " + Currency.getAvailableCurrencies());
	//	Currency.getAvailableCurrencies().forEach(currency -> System.out.println(currency + "\n"));
		String[] argString = new String[]{"AUD 100 in JPY"};
		String fxCurrencyDetailsString = argString[0]; 
		String[] fxCurrencyDetails = fxCurrencyDetailsString.split(" ");
		System.out.println("fxCurrencyDetails[0]" + fxCurrencyDetails[0]);
     StringBuilder displayFXConversionResponse =new StringBuilder(60);
     DecimalFormat df = new DecimalFormat("0.00");
     System.out.println("" + df.format(new BigDecimal(10.00)));
     System.out.println("" + df.format(new BigDecimal(11)));
     
     System.out.println("doble"+ new BigDecimal(10.00).doubleValue());
     BigDecimal baseCurrency1 = new BigDecimal(10.10555);
     BigDecimal termCurrency1 = new BigDecimal(11.556666);
     baseCurrency1.setScale(2,RoundingMode.UP);
     termCurrency1.setScale(3,RoundingMode.UP);
     System.out.println("JPY "+ baseCurrency1 +"= " + "USD " + termCurrency1 );
     
		//displayFXConversionResponse.append("USD").append(" ").append(new BigDecimal(10.00)).append(" ");
		//displayFXConversionResponse.append("=").append(" ").append("AUD").append(" ").append(new BigDecimal(11.95));
		
		System.out.println("displayFXConversionResponse.." +displayFXConversionResponse.toString());
		//return displayFXConversionResponse.toString();
		
		Currency.getInstance("AUD");
		String myNum ="11.22";
		BigDecimal myNumB = new BigDecimal(myNum);
		myNumB.setScale(2);
		System.out.println("myNumB..." +  myNumB.toString());
		System.out.println(myNumB.scale());
		CurrencyPair  usdAud = new CurrencyPair("USD", "AUD");
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
		
		
		
		crossViaLookUpMap.put("USD", viaUSDCurrencyPairList);
		crossViaLookUpMap.put("EUR", viaEURCurrencyPairList);
		
		
		String baseCurrency ="GBP";
		String termCurrency ="NOK";
		BigDecimal baseCurrAmt = new BigDecimal(1.00);
		BigDecimal termCurrAmt = new BigDecimal(0.00);
		double exchangeRate =0.00;
		if(baseCurrency.equalsIgnoreCase(termCurrency)){
			System.out.println("Base and Term Cuurencies are same. So Rate is One");
			termCurrAmt = baseCurrAmt;
		}else{
		CurrencyPair testPair= new CurrencyPair(baseCurrency,termCurrency);
		String currencyPairRelation=fetchInputCurrenciesRelation(testPair);
		System.out.println("currencyPairRelation.."  +  currencyPairRelation);
	
		termCurrAmt=computeFXAmountDirect(baseCurrency, termCurrency, baseCurrAmt);
		termCurrAmt =formatConvertedAmount(termCurrAmt, Currency.getInstance(termCurrency));
		System.out.println("termCurrAmt.." +termCurrAmt);
		//computeFXAmount(currencyPairRelation,testPair,baseCurrAmt);
	
		}
	}	
		
	private static String fetchInputCurrenciesRelation(CurrencyPair currencyPair){
		
		String crossViaCurrency =null;
		if(directionLookUpMap.keySet().contains(currencyPair)){
			System.out.println("It is a direct or Indirect  look Up");
			crossViaCurrency = directionLookUpMap.get(currencyPair);
			
		}else{
			
			System.out.println("Involves Via curr");
			
			for(Map.Entry entry:crossViaLookUpMap.entrySet()){
					    System.out.print(entry.getKey());
							List<CurrencyPair> crossCurrencyList = (List<CurrencyPair>)entry.getValue();
							if(crossCurrencyList.contains(currencyPair)){
								crossViaCurrency= entry.getKey().toString();
							}
				}
			}
		  return crossViaCurrency;
		}

	private static double computeExchangeRate(String lookUpType,CurrencyPair currencyPair){
		double exchangeRate = 0.00;
		
		
		if(lookUpType.equalsIgnoreCase(LookUpType.DIRECT.getValue())){
			 exchangeRate=exchRateMap.get(currencyPair);
		}else if(lookUpType.equalsIgnoreCase(LookUpType.INVERSE.getValue())){
			exchangeRate =1/(exchRateMap.get(new CurrencyPair(currencyPair.getTermCurrKey(), currencyPair.getBaseCurrKey())));
		}
		
		
		return exchangeRate;
	}
	
	private static BigDecimal computeFXAmount(String currPairLookUp, CurrencyPair currencyPair, BigDecimal baseCurrencyAmt){
		
		double currPairExchRate= 0.00;
		BigDecimal termCurrencyAmount =new BigDecimal(0.00);
		
		if(currPairLookUp.equalsIgnoreCase(LookUpType.DIRECT.getValue() )||
				currPairLookUp.equalsIgnoreCase(LookUpType.INVERSE.getValue() )   ){
			System.out.println("Computing Amount for Direct/Indirect relation");
			currPairExchRate =computeExchangeRate(currPairLookUp,currencyPair);
			System.out.println("currPairExchRate.. " + currPairExchRate);
			termCurrencyAmount = baseCurrencyAmt.multiply(new BigDecimal(currPairExchRate));
		}else{
			computeAmtforViaCurrencies(currencyPair,baseCurrencyAmt,currPairLookUp);
		}
		
		return termCurrencyAmount;
		
	}

	private static BigDecimal computeAmtforViaCurrencies(CurrencyPair currencyPair,
			BigDecimal baseCurrAmt,String lookUpType){
		
		System.out.println("In computeAmtforViaCurrencies " + lookUpType);
		CurrencyPair baseViaCurrPair = new CurrencyPair(currencyPair.getBaseCurrKey(),lookUpType);
		String baseViaCurrPairRel = fetchInputCurrenciesRelation(baseViaCurrPair);
		System.out.println("baseViaCurrPairRel..." + baseViaCurrPairRel);
		
		BigDecimal baseToViaCurrAmount= baseCurrAmt;
		BigDecimal convertedCurrAmount= null;
		double baseViaCurrPairExchRate= 0.00;
		if(baseViaCurrPairRel.equalsIgnoreCase(LookUpType.DIRECT.getValue()) || 
				baseViaCurrPairRel.equalsIgnoreCase(LookUpType.INVERSE.getValue())){
			 baseViaCurrPairExchRate=computeExchangeRate(baseViaCurrPairRel, baseViaCurrPair);
			 baseToViaCurrAmount = baseCurrAmt.multiply(new BigDecimal(baseViaCurrPairExchRate));
		}else{
			CurrencyPair crossViaPair = new CurrencyPair(baseViaCurrPair.getBaseCurrKey(),baseViaCurrPairRel);
			computeAmtforViaCurrencies(crossViaPair,baseToViaCurrAmount,baseViaCurrPairRel);
		}
		
		CurrencyPair viaTermCurrPair = new CurrencyPair(lookUpType, currencyPair.getTermCurrKey());
		String viaTermCurrPairRel =fetchInputCurrenciesRelation(viaTermCurrPair);
		System.out.println("viaTermCurrPairRel..." + viaTermCurrPairRel);
		double viaTermCurrPairExchRate=computeExchangeRate(viaTermCurrPairRel, viaTermCurrPair);
		convertedCurrAmount = baseToViaCurrAmount.multiply(new BigDecimal(viaTermCurrPairExchRate));
		System.out.println("convertedCurrAmount..." +convertedCurrAmount);
		
		return formatConvertedAmount(convertedCurrAmount,Currency.getInstance(currencyPair.getTermCurrKey()));
	}
	
	private static BigDecimal formatConvertedAmount(BigDecimal calculatedAmount, Currency termCurrency){
		return calculatedAmount.setScale(termCurrency.getDefaultFractionDigits(), RoundingMode.UP);
		
	}
	
	
	private static BigDecimal computeFXAmountDirect(String baseCurrencyCode, String termCurrencyCode, BigDecimal baseCurrAmt) {
		
		CurrencyPair  currencyPair = new CurrencyPair(baseCurrencyCode, termCurrencyCode);
		String currencyPairRelation = fetchInputCurrenciesRelation(currencyPair);
		double currPairExchRate= 0.00;
		Stack<CurrencyPair> currencyPairStack =new Stack<>();
		
		BigDecimal termCurrencyAmount =baseCurrAmt;
		if(currencyPairRelation.equalsIgnoreCase(LookUpType.DIRECT.getValue() )||
				currencyPairRelation.equalsIgnoreCase(LookUpType.INVERSE.getValue() )   ){
			System.out.println("Computing Amount for Direct/Indirect relation");
			currPairExchRate =computeExchangeRate(currencyPairRelation,currencyPair);
			System.out.println("currPairExchRate.. " + currPairExchRate);
			termCurrencyAmount = baseCurrAmt.multiply(new BigDecimal(currPairExchRate));
			//return termCurrencyAmount;
		}else{
			currencyPairStack.push(new CurrencyPair(currencyPairRelation, termCurrencyCode));
			currencyPairStack.push(new CurrencyPair(baseCurrencyCode, currencyPairRelation));
			
			do {
					CurrencyPair currentCurrencyPair = currencyPairStack.pop();
					System.out.println("currentCurrencyPair.getBaseCurrKey().." + currentCurrencyPair.getBaseCurrKey());
					System.out.println("currentCurrencyPair.getTermCurrKey().." + currentCurrencyPair.getTermCurrKey());
					termCurrencyAmount = computeFXAmountDirect(currentCurrencyPair.getBaseCurrKey(),currentCurrencyPair.getTermCurrKey(), termCurrencyAmount);
				} while (!currencyPairStack.isEmpty());
			
		}
		return termCurrencyAmount;
	}
}
