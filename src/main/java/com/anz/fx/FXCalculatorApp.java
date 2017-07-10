package com.anz.fx;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import com.anz.fx.exception.UnSupportedCurrencyException;
import com.anz.fx.service.CrossViaCurrencyLookUpService;
import com.anz.fx.service.ExchangeRateLoaderService;
import com.anz.fx.service.FXCalculatorService;

/**
 * FX Calculator App 
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
	public void run(String... args) throws Exception {
		try {
			System.out.println("Please enter Below Details in following format");
			System.out.println("<Base Currency Code> <Amount to be converted> in <Term CurrencyCode>");
			String[] fxCurrencyDetails = args;
			System.out.println("n run method.." + fxCurrencyDetails);
			if(fxCurrencyDetails != null && fxCurrencyDetails.length >0 ){
				
				System.out.println("Base Curr: " + fxCurrencyDetails[0]);
				System.out.println("Base Amount to be converted: " + fxCurrencyDetails[1]);
				System.out.println("In:.." + fxCurrencyDetails[2]);
				System.out.println("Term Currency:.." + fxCurrencyDetails[3]);

				
				
		        BigDecimal convertedAmount=fxCalcService.calculateFXAmount( fxCurrencyDetails[0],  new BigDecimal(fxCurrencyDetails[1]), fxCurrencyDetails[3]);
				System.out.println("convertedAmount is..." +convertedAmount);
			}

		} /*catch (UnSupportedCurrencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



}
