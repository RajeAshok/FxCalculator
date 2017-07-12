package com.anz.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anz.fx.exception.FXDetailValidationException;
import com.anz.fx.exception.UnSupportedCurrencyException;
import com.anz.fx.service.FXCalculatorService;

/**
 * 
 * FX Calculator Application :
 * Calculates the foreign exchange Amount for a given pair of currency codes and the exchange amount 
 * Input should be specified in the format: <BASE CURRENCY CODE> <AMOUNT> IN <TERM CURRENCY CODE>
 * Application can be run via IDE Command line Argument or from Command line directly
 *
 */

@SpringBootApplication
public class FXCalculatorApp implements CommandLineRunner {

	@Autowired
	FXCalculatorService fxCalcService;

	private static final Logger LOG = LoggerFactory.getLogger(FXCalculatorApp.class);

	public static void main(String[] args){

		SpringApplication.run(FXCalculatorApp.class,args);

	}

	@Override
	public void run(String... args) throws FXDetailValidationException, UnSupportedCurrencyException{

		System.out.println("Please enter FX details below in  the following format :");
		System.out.println("<Base Currency Code> <Amount to be converted> in <Term CurrencyCode>");

		String baseCurrencyCode="";
		String termCurrencyCode ="";
		String baseCurrencyAmount ="";
		String[] fxCurrencyDetails = null;
		try{	
			if(args.length ==1){                                           //Input from Command line
				String fxCurrencyDetailsString = args[0]; 
				fxCurrencyDetails = fxCurrencyDetailsString.split(" ");
			}else{ //                                                      //Input from IDE Command line Run Configurations
				fxCurrencyDetails = args;	                   
			}

			if(fxCurrencyDetails != null && fxCurrencyDetails.length > 0 ){
				if(fxCurrencyDetails.length != 4){
					String validationErrorMesssage = "Please enter details in correct format : <BaseCurrencyCode> <BaseCurrencyAmount> in <TermcurrencyCode>";
					throw new FXDetailValidationException(validationErrorMesssage);
				}
				baseCurrencyCode=fxCurrencyDetails[0];
				baseCurrencyAmount= fxCurrencyDetails[1];
				termCurrencyCode= fxCurrencyDetails[3];
			}

			String displayMessage=fxCalcService.computeFXConversion(baseCurrencyCode, baseCurrencyAmount, termCurrencyCode);
			LOG.debug("In FXCalculatorApp class, Completed  computeFXConversion . Returned conversion detail is "+ displayMessage );
            System.out.println("****************************OUTPUT****************************");
            System.out.println(displayMessage);
            System.out.println("****************************OUTPUT****************************");
		}catch (FXDetailValidationException fXDetailValidationException){
			System.out.println("****************ERROR*****************************************");
			System.out.println(fXDetailValidationException.getValidationErrorMessage());
			LOG.error(fXDetailValidationException.getValidationErrorMessage());
			System.out.println("****************ERROR*****************************************");

		}catch (UnSupportedCurrencyException unSupportedCurrencyException){
			System.out.println("****************ERROR******************************************");
			System.out.println(unSupportedCurrencyException.getUnSupportedCurrencyErrorMessage());
			LOG.error(unSupportedCurrencyException.toString());
			System.out.println("****************ERROR*******************************************");
		}
	}
}

