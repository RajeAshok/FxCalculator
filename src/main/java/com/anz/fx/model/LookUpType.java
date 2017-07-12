package com.anz.fx.model;
/**
 * 
 * @author AshRaje
 *Enum to represent the cross via look up detail for currency pairs
 */
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
