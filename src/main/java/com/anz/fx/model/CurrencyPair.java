package com.anz.fx.model;

public class CurrencyPair {
	private  String baseCurrKey;

	private  String termCurrKey;

	public CurrencyPair( String baseCurrKey,  String termCurrKey){
		this.baseCurrKey= baseCurrKey;
		this.termCurrKey = termCurrKey;
	}

	@Override
	public boolean equals(final Object O){
		if(this == O ) return true;
		if(!(O instanceof CurrencyPair)) 
			return false;

		CurrencyPair currencyPair = (CurrencyPair)O;
		return (baseCurrKey.equalsIgnoreCase(currencyPair.baseCurrKey)   && termCurrKey.equalsIgnoreCase(currencyPair.termCurrKey));

	}

	@Override
	public int hashCode(){
		return baseCurrKey.hashCode()+ termCurrKey.hashCode();

	}

	public String getBaseCurrKey() {
		return baseCurrKey;
	}

	public String getTermCurrKey() {
		return termCurrKey;
	}

}
