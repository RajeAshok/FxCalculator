package com.anz.fx.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.exception.UnSupportedCurrencyException;
import com.anz.fx.model.CurrencyPair;
import com.anz.fx.model.LookUpType;
import com.anz.fx.service.CrossViaCurrencyLookUpService;
import com.anz.fx.service.ExchangeRateLoaderService;
import com.anz.fx.service.FXCalculatorService;
import com.anz.fx.util.CrossCurrencyLookUpLoader;
import com.anz.fx.util.ExchangeRateLoader;

@Service
public class FXCalculatorServiceImpl implements FXCalculatorService {
	
	static final Logger LOG = LoggerFactory.getLogger(FXCalculatorServiceImpl.class);
	
	

	@Autowired
	private CrossViaCurrencyLookUpService crossViaCurrencyLookUpService;

	@Autowired
	private ExchangeRateLoaderService exchangeRateLoaderService;

	@Override
	public String computeFXConversion(String baseCurrencyCode,
			String baseCurrencyAmountString, String termCurrencyCode) throws FXDetailValidationException, UnSupportedCurrencyException {

		crossViaCurrencyLookUpService.loadCrossViaCurrencyLookUpFromFile();
		exchangeRateLoaderService.loadBaseTermCurrencyExchangeRates();
		validateBaseTermCurrencies(baseCurrencyCode,termCurrencyCode);
		BigDecimal baseCurrencyAmount = new BigDecimal(0.00);
		
		baseCurrencyAmount=validateBaseCurrencyAmount(baseCurrencyAmountString);

		BigDecimal termCurrencyAmount = new BigDecimal(0.00);
		boolean sameBaseTermCurrency = checkForSameBaseTermCurrency(
				baseCurrencyCode, termCurrencyCode);

		if (sameBaseTermCurrency) {
			termCurrencyAmount = baseCurrencyAmount;
		} else {
			termCurrencyAmount = calculateFXAmount(baseCurrencyCode,termCurrencyCode, baseCurrencyAmount);
		}

		baseCurrencyAmount=formatConvertedAmount(baseCurrencyAmount,Currency.getInstance(baseCurrencyCode));
		termCurrencyAmount = formatConvertedAmount(termCurrencyAmount,Currency.getInstance(termCurrencyCode));
		String displayResponseMessage = displayResponseMessage(baseCurrencyCode,termCurrencyCode,baseCurrencyAmount,termCurrencyAmount);
		return displayResponseMessage;
	}

	/**
	 * 
	 * @param baseCurrencyCode
	 * @param termCurrencyCode
	 * @return
	 */
	protected boolean checkForSameBaseTermCurrency(String baseCurrencyCode,
			String termCurrencyCode) {
		if (baseCurrencyCode.equalsIgnoreCase(termCurrencyCode)) {
			return true;
		} else {
			return false;
		}
	}
	
	protected String fetchCurrencyPairLookUpType(CurrencyPair currencyPair) {
		String lookUpType = null;

		Map<String, List<CurrencyPair>> crossViaCurrencyLookUpMap = null;
		Map<CurrencyPair, String> directionLookUpMap = null;

		crossViaCurrencyLookUpMap = crossViaCurrencyLookUpService
				.fetchCrossViaCurrencyLookUpMap();
		directionLookUpMap = crossViaCurrencyLookUpService
				.fetchDirectIndirectCurrLookUpMap();

		if (directionLookUpMap.keySet().contains(currencyPair)) {
			System.out.println("It is a direct or Indirect  look Up");
			lookUpType = directionLookUpMap.get(currencyPair);

		} else {
			System.out.println("Involves Via curr");
			for (Map.Entry entry : crossViaCurrencyLookUpMap.entrySet()) {
				List<CurrencyPair> crossCurrencyList = (List<CurrencyPair>) entry.getValue();
				if (crossCurrencyList.contains(currencyPair)) {
					lookUpType = entry.getKey().toString();
				}
			}
		}
		System.out.println("lookUpType returned.." + lookUpType);
		return lookUpType;
	}

	/**
	 * Compute exchange rate based on the loaded Map with Currency Pair and
	 * exchange rate details for certain currencyPairs with INVERSE relation ,
	 * need to look up with proper order of Currencies in CurrencyPair object
	 * 
	 * @param lookUpType
	 * @param currencyPair
	 * @return
	 */
	protected double computeExchangeRate(String lookUpType, CurrencyPair currencyPair) {
		double exchangeRate = 0.00;
		Map<CurrencyPair, Double> baseTermCurrencyExchangeRateMap = exchangeRateLoaderService
				.fetchBaseCurrencyExchangeRateMap();
		if (lookUpType.equalsIgnoreCase(LookUpType.DIRECT.getValue())) {
			exchangeRate = baseTermCurrencyExchangeRateMap.get(currencyPair);
		} else if (lookUpType.equalsIgnoreCase(LookUpType.INVERSE.getValue())) {
			exchangeRate = 1 / (baseTermCurrencyExchangeRateMap
					.get(new CurrencyPair(currencyPair.getTermCurrKey(),
							currencyPair.getBaseCurrKey())));
		}
		return exchangeRate;
	}

	/**
	 * Calculate the Amount in the term currency provided as input using
	 * exchange rate Used this formula to calculate the converted amount:
	 * "termCurrencyAmount = baseCurrencyAmount * exchange rate for that
	 * BASE/TERM performed recursion as the currency look up involves cross via
	 * look ups other than direct and Indirect
	 * 
	 * @param baseCurrencyCode
	 * @param termCurrencyCode
	 * @param baseCurrencyAmount
	 * @return
	 */
	protected BigDecimal calculateFXAmount(String baseCurrencyCode,
			String termCurrencyCode, BigDecimal baseCurrencyAmount) {

		CurrencyPair currencyPair = new CurrencyPair(baseCurrencyCode,
				termCurrencyCode);
		String currencyPairRelation = fetchCurrencyPairLookUpType(currencyPair);
		double currencyPairExchangeRate = 0.00;
		Stack<CurrencyPair> currencyPairStack = new Stack<>();
		BigDecimal termCurrencyAmount = baseCurrencyAmount;
		if (currencyPairRelation.equalsIgnoreCase(LookUpType.DIRECT.getValue())
				|| currencyPairRelation.equalsIgnoreCase(LookUpType.INVERSE.getValue())) {
			System.out.println("Computing Amount for Direct/Indirect relation");
			currencyPairExchangeRate = computeExchangeRate(currencyPairRelation, currencyPair);
			System.out.println("currPairExchRate.. " + currencyPairExchangeRate);
			termCurrencyAmount = baseCurrencyAmount.multiply(new BigDecimal(currencyPairExchangeRate));
			System.out.println("intermediate termCurrencyAmount.." +termCurrencyAmount);
		} else {
			currencyPairStack.push(new CurrencyPair(currencyPairRelation,
					termCurrencyCode));
			currencyPairStack.push(new CurrencyPair(baseCurrencyCode,
					currencyPairRelation));

			do {
				CurrencyPair currentCurrencyPair = currencyPairStack.pop();
				System.out.println("currentCurrencyPair.getBaseCurrKey().."
						+ currentCurrencyPair.getBaseCurrKey());
				System.out.println("currentCurrencyPair.getTermCurrKey().."
						+ currentCurrencyPair.getTermCurrKey());
				termCurrencyAmount = calculateFXAmount(
						currentCurrencyPair.getBaseCurrKey(),
						currentCurrencyPair.getTermCurrKey(),
						termCurrencyAmount);
			} while (!currencyPairStack.isEmpty());
		}
		return termCurrencyAmount;
	}

	 //Utility Method to format the currency based on precision
	protected BigDecimal formatConvertedAmount(BigDecimal calculatedAmount,Currency termCurrency) {
		System.out.println("final convertedAmt " + calculatedAmount.setScale(termCurrency.getDefaultFractionDigits(), RoundingMode.UP) );
		return calculatedAmount.setScale(termCurrency.getDefaultFractionDigits(), RoundingMode.UP);
	}
	
  
	protected void validateBaseTermCurrencies(String baseCurrencyCode,String termCurrencyCode) 
			 throws FXDetailValidationException, UnSupportedCurrencyException{

		StringBuilder displayErrorMessage= new StringBuilder("Validation Error.");
		if(baseCurrencyCode == null || baseCurrencyCode.isEmpty()){
			displayErrorMessage.append("Base Currency Code cannot be empty .Please enter a value. ");
			throw new FXDetailValidationException(displayErrorMessage.toString());
		}
		if(termCurrencyCode == null || termCurrencyCode.isEmpty()){
			displayErrorMessage.append("Term Currency Code cannot be empty .Please enter a value. ");
			throw new FXDetailValidationException(displayErrorMessage.toString());
		}
			
		checkIfBaseTermCurrencyIsSupported(baseCurrencyCode,termCurrencyCode);	
	}

	protected void checkIfBaseTermCurrencyIsSupported(String baseCurrencyCode,String termCurrencyCode) throws UnSupportedCurrencyException, FXDetailValidationException {
		List<Currency> supportedFXCurrenciesList = crossViaCurrencyLookUpService.fetchSupportedFXCurrenciesList();

		StringBuilder displayErrorMessage = new StringBuilder("Unable to find rate for " + baseCurrencyCode +"/" + termCurrencyCode + ".") ;
		Currency baseCurrency;
		Currency termCurrency ;
		try{
			baseCurrency=Currency.getInstance(baseCurrencyCode);
		}catch(IllegalArgumentException illegalArgumentException){
			displayErrorMessage.append("Base currency code provided is not a valid ISO 4217 currency code .Please enter a valid Base currency code. ");
			throw new FXDetailValidationException(displayErrorMessage.toString());
		}
		try{
			termCurrency=Currency.getInstance(termCurrencyCode);
		}catch(IllegalArgumentException illegalArgumentException){
			displayErrorMessage.append("Term currency code provided is not a valid ISO 4217 currency code .Please enter a valid Term currency code. ");
			throw new FXDetailValidationException(displayErrorMessage.toString());
		}
		
		if (!supportedFXCurrenciesList.contains(baseCurrency)) {
			displayErrorMessage.append("Base Currency Code provided is not supported .Please enter a valid Base Currency Code. ");
			throw new UnSupportedCurrencyException(displayErrorMessage.toString());
		}
		if (!supportedFXCurrenciesList.contains(termCurrency)) {
			displayErrorMessage.append("Term Currency Code provided is not supported .Please enter a valid Term Currency Code. ");
			throw new UnSupportedCurrencyException(displayErrorMessage.toString());
		}

	}
	
	protected BigDecimal validateBaseCurrencyAmount(String baseCurrAmount) throws FXDetailValidationException{
		
		BigDecimal baseCurrencyAmount = new BigDecimal(0.00);
		try{
			baseCurrencyAmount = new BigDecimal(baseCurrAmount);
		}catch(NumberFormatException ne){
			String errorMessage ="Error Occured. Enter a valid number in Amount field";
			throw new FXDetailValidationException(errorMessage);
		}
		return baseCurrencyAmount;
	}
	
	protected String displayResponseMessage(String baseCurrencyCode, String termCurrencyCode, BigDecimal baseCurrencyAmount, BigDecimal termCurrencyAmount){
		StringBuilder displayFXConversionResponse =new StringBuilder(60);
		DecimalFormat df = new DecimalFormat("0.00");
		displayFXConversionResponse.append(baseCurrencyCode).append(" ").append(baseCurrencyAmount).append(" ");
		displayFXConversionResponse.append("=").append(" ").append(termCurrencyCode).append(" ").append(termCurrencyAmount);
		
		System.out.println("displayFXConversionResponse.." +displayFXConversionResponse.toString());
		return displayFXConversionResponse.toString();
	}

}
