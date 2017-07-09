package com.anz.fx.exception;


public class UnSupportedCurrencyException extends Exception{

	private static final long serialVersionUID = -1915647763120716299L;
	private String errorMessage;
	
	public UnSupportedCurrencyException(String message){
		super();
		this.errorMessage= message;
	}
	
	@Override
	public String toString(){
		return "Currency entered is not Supported.Enter valid currency codes" + this.errorMessage;
	}
}
