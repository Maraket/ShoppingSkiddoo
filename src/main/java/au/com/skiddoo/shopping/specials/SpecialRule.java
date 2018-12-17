package au.com.skiddoo.shopping.specials;

import java.util.List;

import au.com.skiddoo.shopping.products.Product;

public interface SpecialRule {
	public boolean appliesTo(String sku);
	//When applied this will remove any products that this special applies to
	//and return the remaining products
	public void addDiscounts(List<Product> products);
}