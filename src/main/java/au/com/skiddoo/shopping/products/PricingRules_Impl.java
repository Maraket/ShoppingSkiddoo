package au.com.skiddoo.shopping.products;

import java.util.ArrayList;
import java.util.List;

import au.com.skiddoo.shopping.specials.SpecialRule;

public class PricingRules_Impl implements PricingRules {

	List<SpecialRule> rules;
	
	public PricingRules_Impl() {
		rules = new ArrayList<>();
	}
	
	public PricingRules_Impl(List<SpecialRule> rules) {
		this.rules = rules;
	}

	@Override
	public PricingRules addSpecial(SpecialRule sr) {
		rules.add(sr);
		return this;
	}

	@Override
	public PricingRules addSpecial(List<SpecialRule> sr) {
		rules.addAll(sr);
		return this;
	}

	@Override
	public List<Product> applyRules(List<Product> shoppingList) {
		//Apply discounts to all the rules
		rules.stream()
			.forEach(sr -> sr.addDiscounts(shoppingList));
		
		return shoppingList;
	}

}
