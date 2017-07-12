package com.anz.fx.exception;

public class FXDetailValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String validationErrorMessage;
	
	public FXDetailValidationException(){
		super();
	}
	
	public FXDetailValidationException(String validationErrorMesssage){
		super(validationErrorMesssage);
	}
	
	@Override
	public String toString(){
		return "Validation errors in the provided FX details. " + validationErrorMessage;
	}

	public String getValidationErrorMessage() {
		return validationErrorMessage;
	}

	public void setValidationErrorMessage(String validationErrorMessage) {
		this.validationErrorMessage = validationErrorMessage;
	}
}
