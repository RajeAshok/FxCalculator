package com.anz.fx.exception;

/**
 * Custom exception class to handle Unsupported Currency exception
 * @author AshRaje
 *
 */
public class UnSupportedCurrencyException extends Exception{

	private static final long serialVersionUID = -1915647763120716299L;
	private String unSupportedCurrencyErrorMessage;
	
	public UnSupportedCurrencyException(){
		super();
	}
	
	public UnSupportedCurrencyException(String unSupportedCurrencyErrorMessage){
		super(unSupportedCurrencyErrorMessage);
		this.unSupportedCurrencyErrorMessage =unSupportedCurrencyErrorMessage;
		
	}
	
	@Override
	public String toString(){
		return  this.unSupportedCurrencyErrorMessage;
	}

	public String getUnSupportedCurrencyErrorMessage() {
		return unSupportedCurrencyErrorMessage;
	}

	public void setUnSupportedCurrencyErrorMessage(
			String unSupportedCurrencyErrorMessage) {
		this.unSupportedCurrencyErrorMessage = unSupportedCurrencyErrorMessage;
	}
}
