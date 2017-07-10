package com.anz.fx;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.exception.UnSupportedCurrencyException;
import com.anz.fx.service.FXCalculatorService;

/**
 * FX Calculator App : Calculates the foreign exchange Amount for a given pair of currency codes and the exchange amount 
 * Input should be specified in the format: <BASE CURRENCY CODE> <AMOUNT> IN <TERM CURRENCY CODE>
 * @author AshRaje
 *
 */

@SpringBootApplication
public class FXCalculatorApp implements CommandLineRunner {

	@Autowired
	FXCalculatorService fxCalcService;
	
	
	public static void main(String[] args) throws IOException {
			SpringApplication.run(FXCalculatorApp.class,args);
	}

	@Override
	public void run(String... args){
		
			System.out.println("Please enter below Details in following format");
			System.out.println("<Base Currency Code> <Amount to be converted> in <Term CurrencyCode>");
			String[] fxCurrencyDetails = args;
			String baseCurrencyCode="";
			String termCurrencyCode ="";
			String baseCurrencyAmount ="";
			if(fxCurrencyDetails != null && fxCurrencyDetails.length > 0 ){
				if(fxCurrencyDetails.length != 4){
					String validationErrorMesssage = "Please enter details in correct format : <BaseCurrencyCode> <BaseCurrencyAmount> in <TermcurrencyCode>";
					try {
						throw new FXDetailValidationException(validationErrorMesssage);
					} catch (FXDetailValidationException e) {
						System.out.println(e.getMessage());
					}
				}
				baseCurrencyCode=fxCurrencyDetails[0];
				baseCurrencyAmount= fxCurrencyDetails[1];
				termCurrencyCode= fxCurrencyDetails[3];
		
				try {
				     BigDecimal convertedAmount = new BigDecimal(0.00);
					 convertedAmount = fxCalcService.calculateFXAmount(baseCurrencyCode, baseCurrencyAmount, termCurrencyCode);
				} catch (UnSupportedCurrencyException | FXDetailValidationException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				
			}
	}


}
