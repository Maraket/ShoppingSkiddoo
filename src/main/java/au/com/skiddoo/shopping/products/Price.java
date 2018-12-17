// Copyright 2015 Skiddoo Pty Ltd

package au.com.skiddoo.shopping.products;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author jaiew
 */
public class Price {
	// Using Big Decimal ideal for currency (out of the standard jdk)
	private BigDecimal value;
	private Currency currency;
    
	public Price() {
		
		// Should be noted, that default values should be defined using something like a properties file
		// in a production env
		this(new BigDecimal(0), Currency.getInstance("AUD"));
	}
	
	public Price(BigDecimal value) {
		this(value, Currency.getInstance("AUD"));
	}
	
	public Price(BigDecimal value, Currency currency) {
		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}
	
	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "Price [value=" + value + ", currency=" + currency + "]";
	}
}
