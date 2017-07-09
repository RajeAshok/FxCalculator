package com.anz.fx.model;

public enum LookUpType {
	DIRECT("DIR"),
	INVERSE("INV"),
	UNITY("ONE"),
	VIAUSD("USD"),
	VIAEUR("EUR");
	
	private final String value;
	
	LookUpType(final String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
}
