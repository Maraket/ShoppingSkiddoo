package au.com.skiddoo.shopping.specials;

import static java.util.stream.Collectors.toList;

import java.util.List;
import au.com.skiddoo.shopping.products.Product;

public class XforY implements SpecialRule {
	private int X;
	private int Y;
	private int diff;
	private Product toCompare;
	private String name;
	private boolean excludeOtherDiscounts;
	
	public XforY(Product toCompare, int x, int y, boolean excludeOtherDiscounts) throws DiscountInvalidException {
		this(toCompare, x, y, "Buy " + x + " " + toCompare.getName() + " for the price of " + y, excludeOtherDiscounts);
	}
	
	public XforY(Product toCompare, int x, int y, String name, boolean excludeOtherDiscounts) throws DiscountInvalidException {
		X = x;
		Y = y;
		diff = X - Y;
		this.toCompare = toCompare;
		this.name = name;
		this.excludeOtherDiscounts = excludeOtherDiscounts;
		if(diff <= 0)
			throw new DiscountInvalidException("This discount offers no benefit");
	}

	@Override
	public void addDiscounts(List<Product> products) {
		List<Product> eligible = products.stream()
				.filter(x -> appliesTo(x.getSku()))
				.collect(toList());
		// First check there are enough products for the special
		if(eligible.size() >= X) {
			if(excludeOtherDiscounts)
				eligible = eligible.stream()
					.filter(x -> x.getDiscounts().size() > 0)
					.collect(toList());
			int applicableTo = ((eligible.size() - (eligible.size() % X))/X) * diff;
			
			eligible.stream()
				.limit(applicableTo)
				.forEach(x -> x.addDiscount(new Discount(x.afterDiscounts(), name)));
		}
	}

	@Override
	public boolean appliesTo(String sku) {
		return toCompare.getSku().equals(sku);
	}
}

