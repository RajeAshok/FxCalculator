package com.anz.fx.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.anz.fx.exception.UnSupportedCurrencyException;

@Component
public interface FXCalculatorService {

	public BigDecimal calculateFXAmount(String baseCurrencyCode, BigDecimal baseCurrencyAmount, String termCurrencyCode) 
			throws UnSupportedCurrencyException;
}
