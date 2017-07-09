package com.anz.fx.model;

public enum CrossViaCurrency {

	VIAUSD("USD"),
	VIAEUR("EUR");
	
	
	private final String value;
	
	CrossViaCurrency(final String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
