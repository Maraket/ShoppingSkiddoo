// Copyright 2015 Skiddoo Pty Ltd

package au.com.skiddoo.shopping.products;

import java.util.ArrayList;
import java.util.List;

import au.com.skiddoo.shopping.specials.Discount;

/**
 * @author jaiew
 */
public class Product {

    private final String sku;
    private final String name;
    private final Price price;
    private List<Discount> discounts;

    public Product(String sku, String name, Price price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        discounts = new ArrayList<>();
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
    
    public Product addDiscount(Discount discount) {
    	discounts.add(discount);
    	return this;
    }
    
    
    public List<Discount> getDiscounts() {
		return discounts;
	}

	public Price afterDiscounts() {
		if(!discounts.isEmpty())
			return discounts.stream().map(Discount::getValue).reduce(price, (a, b) -> new Price(a.getValue().subtract(b.getValue())));
		else
			return price;
    }
}
