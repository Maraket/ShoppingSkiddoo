package au.com.skiddoo.shopping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import au.com.skiddoo.shopping.products.NoSuchProductException;
import au.com.skiddoo.shopping.products.Price;
import au.com.skiddoo.shopping.products.PricingRules;
import au.com.skiddoo.shopping.products.Product;
import au.com.skiddoo.shopping.products.ProductStore;

public class Checkout implements CheckoutRegister {	
	
	private PricingRules rules;
	private ProductStore store;
	
	private List<Product> shoppingList;
	public Checkout(PricingRules pr, ProductStore ps) {
		this.rules = pr;
		this.store = ps;
		shoppingList = new ArrayList<>();
	}
	
	@Override
	public CheckoutRegister read(String sku) throws NoSuchProductException{
		shoppingList.add(store.getProduct(sku));
		return this;
	}

	@Override
	public Price total() {
		Price base = new Price(BigDecimal.ZERO);
		return rules.applyRules(shoppingList).stream().map(Product::afterDiscounts).reduce(base,(a, b) -> new Price(a.getValue().add(b.getValue())));
	}

}
