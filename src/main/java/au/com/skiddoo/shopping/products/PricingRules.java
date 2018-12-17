 package au.com.skiddoo.shopping.products;

import java.util.List;

import au.com.skiddoo.shopping.specials.SpecialRule;

public interface PricingRules {
	public PricingRules addSpecial(SpecialRule sr);
	public PricingRules addSpecial(List<SpecialRule> sr);
	public List<Product> applyRules(List<Product> shoppingList);
}
