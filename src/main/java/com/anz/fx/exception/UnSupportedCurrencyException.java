package com.anz.fx.exception;


public class UnSupportedCurrencyException extends Exception{

	private static final long serialVersionUID = -1915647763120716299L;
	private String unSupportedCurrencyErrorMessage;
	
	
	public UnSupportedCurrencyException(String unSupportedCurrencyErrorMessage){
		super(unSupportedCurrencyErrorMessage);
		
	}
	
	@Override
	public String toString(){
		return "Currency entered is not Supported.Enter valid currency codes" + this.unSupportedCurrencyErrorMessage;
	}

	public String getUnSupportedCurrencyErrorMessage() {
		return unSupportedCurrencyErrorMessage;
	}

	public void setUnSupportedCurrencyErrorMessage(
			String unSupportedCurrencyErrorMessage) {
		this.unSupportedCurrencyErrorMessage = unSupportedCurrencyErrorMessage;
	}
}
