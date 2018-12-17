package au.com.skiddoo.shopping.specials;

import au.com.skiddoo.shopping.products.Price;

public class Discount {
	private Price value;
	private String name;
	
	public Discount(Price value, String name) {
		this.value = value;
		this.name = name;
	}

	public Price getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
