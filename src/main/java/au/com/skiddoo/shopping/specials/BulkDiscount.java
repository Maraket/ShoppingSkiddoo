package au.com.skiddoo.shopping.specials;

import static java.util.stream.Collectors.toList;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;

import au.com.skiddoo.shopping.products.Price;
import au.com.skiddoo.shopping.products.Product;

public class BulkDiscount implements SpecialRule {
	
	private Product toCompare;
	private int count;
	private Price toCharge;
	private Discount discount;
	
	private Predicate<Product> test;

	private boolean excludeOtherDiscounts;
	
	public BulkDiscount(Product toCompare, int count, Price toCharge, boolean excludeOtherDiscounts) throws DiscountInvalidException {
		this(toCompare, count, toCharge, "Bulk discount on " + toCompare.getName() + " for purchasing more then " + count, excludeOtherDiscounts);
	}

	public BulkDiscount(Product p, int count, Price price, String name, boolean excludeOtherDiscounts) throws DiscountInvalidException {
		toCompare = p;
		this.count = count;
		toCharge = price;
		discount = new Discount(
				new Price(toCompare.getPrice().getValue().subtract(toCharge.getValue())), name);
		this.excludeOtherDiscounts = excludeOtherDiscounts;
		
		if(discount.getValue().getValue().compareTo(BigDecimal.ZERO) <= 0)
			throw new DiscountInvalidException("Discount offers no benefit");
		
		test = x -> appliesTo(x.getSku());
	}
	
	@Override
	public void addDiscounts(List<Product> products) {
		List<Product> eligibleProducts = products.stream()
				.filter(test)
				.collect(toList());
		
		if(eligibleProducts.size() >= count)			
			eligibleProducts.stream()
				.filter(x -> !(excludeOtherDiscounts && x.getDiscounts().size() > 0))
				.forEach(x -> x.addDiscount(discount));
	}

	@Override
	public boolean appliesTo(String sku) {
		return toCompare.getSku().equals(sku);
	}
}
