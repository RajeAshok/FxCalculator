package com.anz.fx.service;

import org.springframework.stereotype.Component;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.exception.UnSupportedCurrencyException;

@Component
public interface FXCalculatorService {

	/**
	 * Calculates FX conversion rate for a given base/term currency pair
	 * @param baseCurrencyCode
	 * @param baseCurrencyAmount
	 * @param termCurrencyCode
	 * @return
	 * @throws FXDetailValidationException
	 * @throws UnSupportedCurrencyException
	 */
	String computeFXConversion(String baseCurrencyCode, String baseCurrencyAmount, 
			String termCurrencyCode) throws FXDetailValidationException, UnSupportedCurrencyException ;
	
}
