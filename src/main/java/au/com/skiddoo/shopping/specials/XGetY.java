package au.com.skiddoo.shopping.specials;

import java.util.List;
import au.com.skiddoo.shopping.products.Product;

public class XGetY implements SpecialRule{

	private Product X;
	private Product Y;
	private String name;
	private boolean excludeOtherDiscounts;
	
	public XGetY(Product X, Product Y, boolean excludeOtherDiscounts){
		this(X, Y, "Buy " + X.getName() + " get " + Y.getName() + " Free", excludeOtherDiscounts);
	}
	
	public XGetY(Product X, Product Y, String name, boolean excludeOtherDiscounts){
		this.X = X;
		this.Y = Y;
		this.name = name;
		this.excludeOtherDiscounts = excludeOtherDiscounts;
	}
	
	@Override
	public void addDiscounts(List<Product> products) {
		//Remove the products that will apply to the special
		long eligibleFor = products.stream()
				.filter(x -> appliesTo(x.getSku()))
				.count();
		products.stream()
			.filter(x -> isBonus(x.getSku()) && !(excludeOtherDiscounts && x.getDiscounts().size() > 0))
			.limit(eligibleFor)
			.forEach(x -> x.addDiscount(new Discount(x.afterDiscounts(), name)));
	}

	@Override
	public boolean appliesTo(String sku) {
		return X.getSku().equals(sku);
	}
	
	private boolean isBonus(String sku) {
		return Y.getSku().equals(sku);
	}
}
