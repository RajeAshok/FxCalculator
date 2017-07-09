package com.anz.fx.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.anz.fx.exception.UnSupportedCurrencyException;
import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;
import com.anz.fx.util.CrossCurrencyLookUpLoader;
import com.anz.fx.util.ExchangeRateLoader;

@Component
public class FXCalculatorServiceImpl implements FXCalculatorService {

	@Autowired
	private CrossCurrencyLookUpLoader crossCurrencyLookUpLoader;

	@Autowired
	private ExchangeRateLoader exchangeRateLoader;

	private Map<String,List<CurrencyPair>> crossViaCurrencyLookUpMap = null ;

	private  Map<CurrencyPair,String> directionLookUpMap =null;

	private  Map<CurrencyPair,Double> baseTermCurrencyExchangeRateMap =null;
	
	private ResourceLoader resourceLoader;
	
	@Value("classpath:FXCrossViaLookUp.txt")
	private Resource res;

	
	/*@Autowired
	public FXCalculatorServiceImpl(ResourceLoader resourceLoader){
		resourceLoader =this.resourceLoader;
	}*/

	
	@PostConstruct
    public void init() throws Exception {
        try {
            System.out.println("In post construct.....");
           /* InputStream inputStream=res.getInputStream();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString());   //Prints the string content read from input stream
            reader.close();*/
            
        	List<String> baseTermCurrencyLookUpDetails = Files.readAllLines(Paths.get(res.getURI()),StandardCharsets.UTF_8);
        	System.out.println("Input number of lines..." + baseTermCurrencyLookUpDetails.size());
        	char[] termCurrencyArray = new char[]{};
        	
        	termCurrencyArray = (baseTermCurrencyLookUpDetails.get(0)).toCharArray() ; 
        	List<String> termCurrencyList = new ArrayList<>();
        	for(char eachTermCurr : termCurrencyArray){
        		termCurrencyList.add(String.valueOf(eachTermCurr));
        	}
        	
        	System.out.println("termCurrencyList first item" + termCurrencyList.get(0));
        	System.out.println("baseTermCurrencyLookUpDetails first item" + baseTermCurrencyLookUpDetails.get(0));
        	
        	for(int i=1;i>baseTermCurrencyLookUpDetails.size();i++){
        		
        		
        	}
        	
        	/*for (String line :lines) {
        		
                String[] words = line.split("\\s+");
                System.out.println("One line no of elements" + words.length );
               for (String word : words) {
                	System.out.println("word here..." + word);
                }
        	}*/
        } catch (IOException | NullPointerException e) {
        	
            System.out.println(" reader cound not be initialized. ");
            throw e;
        }
    }

   /* @PreDestroy
    public void preDestroy() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.error("Failed to close the reader.");
            }
        }
    }*/
	
	
	
	
	@Override
	public BigDecimal calculateFXAmount(String baseCurrencyCode,
			BigDecimal baseCurrencyAmount, String termCurrencyCode)
					throws UnSupportedCurrencyException {


		//validateBaseTermCurrencies(baseCurencyCode,termCuurencyCode);
		//validateBaseCurrencyAmount();
		crossViaCurrencyLookUpMap = crossCurrencyLookUpLoader.loadCrossCurrencyLookUpMap();
		directionLookUpMap =crossCurrencyLookUpLoader.loadDirectionLookUpMap();
		//baseTermCurrencyExchangeRateMap = exchangeRateLoader.loadExchangeRates();

		BigDecimal termCurrencyAmount = new BigDecimal(0.00);
		boolean sameBaseTermCurrency=checkForSameBaseTermCurrency(baseCurrencyCode,termCurrencyCode);

		if(sameBaseTermCurrency){
			termCurrencyAmount = baseCurrencyAmount;
		}else{
			termCurrencyAmount=computeFXAmount(baseCurrencyCode, termCurrencyCode, baseCurrencyAmount);
		}

		termCurrencyAmount =formatConvertedAmount(termCurrencyAmount, Currency.getInstance(termCurrencyCode));
		System.out.println("termCurrencyAmount.." +termCurrencyAmount);
		return termCurrencyAmount;
	}

	/**
	 * 
	 * @param baseCurrencyCode
	 * @param termCurrencyCode
	 * @return
	 */
	 boolean checkForSameBaseTermCurrency(String baseCurrencyCode,String termCurrencyCode){
		if(baseCurrencyCode.equalsIgnoreCase(termCurrencyCode)){
			return true;
		}else{
			return false;
		}		
	}


	private String fetchCurrencyPairLookUpType(CurrencyPair currencyPair){
		String lookUpType =null;
		if(directionLookUpMap.keySet().contains(currencyPair)){
			System.out.println("It is a direct or Indirect  look Up");
			lookUpType = directionLookUpMap.get(currencyPair);

		}else{
			System.out.println("Involves Via curr");
			for(Map.Entry entry:crossViaCurrencyLookUpMap.entrySet()){
				List<CurrencyPair> crossCurrencyList = (List<CurrencyPair>)entry.getValue();
				if(crossCurrencyList.contains(currencyPair)){
					lookUpType= entry.getKey().toString();
				}
			}
		}
		System.out.println("lookUpType returned.." +lookUpType);
		return lookUpType;
	}

	/**
	 * Compute exchange rate based on the loaded Map with Currency Pair and exchange rate details
	 * for certain currencyPairs with INVERSE relation , need to look up with proper order of Currencies in CurrencyPair object
	 * @param lookUpType
	 * @param currencyPair
	 * @return
	 */
	 double computeExchangeRate(String lookUpType,CurrencyPair currencyPair){
		double exchangeRate = 0.00;
		
		if(lookUpType.equalsIgnoreCase(LookUpType.DIRECT.getValue())){
			exchangeRate=baseTermCurrencyExchangeRateMap.get(currencyPair);
		}else if(lookUpType.equalsIgnoreCase(LookUpType.INVERSE.getValue())){
			exchangeRate =1/(baseTermCurrencyExchangeRateMap.get(new CurrencyPair(currencyPair.getTermCurrKey(), currencyPair.getBaseCurrKey())));
		}
		return exchangeRate;
	}

	/**
	 * Calculate the Amount in the term currency provided as input using exchange rate 
	 * Used this formula to calculate the converted amount:  "termCurrencyAmount = baseCurrencyAmount * exchange rate for that BASE/TERM
	 * performed recursion as the currency look up involves cross via look ups other than direct and Indirect
	 * @param baseCurrencyCode
	 * @param termCurrencyCode
	 * @param baseCurrencyAmount
	 * @return
	 */
	private BigDecimal computeFXAmount(String baseCurrencyCode, String termCurrencyCode,BigDecimal baseCurrencyAmount){

		CurrencyPair  currencyPair = new CurrencyPair(baseCurrencyCode, termCurrencyCode);
		String currencyPairRelation = fetchCurrencyPairLookUpType(currencyPair);
		double currencyPairExchangeRate= 0.00;
		Stack<CurrencyPair> currencyPairStack =new Stack<>();
		BigDecimal termCurrencyAmount =baseCurrencyAmount;
		if(currencyPairRelation.equalsIgnoreCase(LookUpType.DIRECT.getValue() )||
				currencyPairRelation.equalsIgnoreCase(LookUpType.INVERSE.getValue() )   ){
			System.out.println("Computing Amount for Direct/Indirect relation");
			currencyPairExchangeRate =computeExchangeRate(currencyPairRelation,currencyPair);
			System.out.println("currPairExchRate.. " + currencyPairExchangeRate);
			termCurrencyAmount = baseCurrencyAmount.multiply(new BigDecimal(currencyPairExchangeRate));

		}else{
			currencyPairStack.push(new CurrencyPair(currencyPairRelation, termCurrencyCode));
			currencyPairStack.push(new CurrencyPair(baseCurrencyCode, currencyPairRelation));

			do {
				CurrencyPair currentCurrencyPair = currencyPairStack.pop();
				System.out.println("currentCurrencyPair.getBaseCurrKey().." + currentCurrencyPair.getBaseCurrKey());
				System.out.println("currentCurrencyPair.getTermCurrKey().." + currentCurrencyPair.getTermCurrKey());
				termCurrencyAmount = computeFXAmount(currentCurrencyPair.getBaseCurrKey(),currentCurrencyPair.getTermCurrKey(), termCurrencyAmount);
			} while (!currencyPairStack.isEmpty());

		}
		return termCurrencyAmount;
	}

	private BigDecimal formatConvertedAmount(BigDecimal calculatedAmount, Currency termCurrency){
		return calculatedAmount.setScale(termCurrency.getDefaultFractionDigits(), RoundingMode.UP);

	}

}
