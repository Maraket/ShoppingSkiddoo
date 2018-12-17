package au.com.skiddoo.shopping;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import au.com.skiddoo.shopping.products.NoSuchProductException;
import au.com.skiddoo.shopping.products.Price;
import au.com.skiddoo.shopping.products.Product;
import au.com.skiddoo.shopping.products.ProductStore;
import au.com.skiddoo.shopping.products.ProductStore_Impl;
import au.com.skiddoo.shopping.specials.BulkDiscount;
import au.com.skiddoo.shopping.specials.DiscountInvalidException;


@RunWith(Parameterized.class)
public class BulkDiscountTests {

	private static ProductStore store;
	
	@Parameter(0)
	public String X;
	
	@Parameter(1)
	public int count;
	
	@Parameter(2)
	public BigDecimal price;

	@Parameter(3)
	public List<String> products;
	
	@Parameter(4)
	public BigDecimal resultWithout;

	@Parameter(5)
	public BigDecimal resultWith;

	@Parameters
	public static Collection<Object[]> params(){
		store = new ProductStore_Impl();
		store.addProduct(new Product("ipad", "Super iPad", new Price(BigDecimal.valueOf(649.99))));
		store.addProduct(new Product("macbookpro", "MacBook Pro", new Price(BigDecimal.valueOf(1499.99))));
		store.addProduct(new Product("appletv", "Apple TV", new Price(BigDecimal.valueOf(209.50))));
		store.addProduct(new Product("hdmiadapter", "HDMI adapter", new Price(BigDecimal.valueOf(130))));
	
		return Arrays.asList(new Object[][] {
			{ 
				"ipad", 
				4,
				BigDecimal.valueOf(499.99),
				Arrays.asList("appletv","ipad","ipad","appletv","ipad","ipad","ipad"), 
				BigDecimal.valueOf(3668.95), 
				BigDecimal.valueOf(2918.95) 
			},{ 
				"ipad", 
				4,
				BigDecimal.valueOf(499.99),
				Arrays.asList("appletv","ipad","ipad","appletv","ipad"), 
				BigDecimal.valueOf(2368.97), 
				BigDecimal.valueOf(2368.97) 
			}
		});
	}
	
	@Test
	public void test() {
		BulkDiscount test;
		try {
			test = new BulkDiscount(store.getProduct(X), count, new Price(price), false);
	
			List<Product> testList = products.stream().map(x -> {
				try {
					return store.getProduct(x);
				} catch (NoSuchProductException e) {
					fail("This should never occur");
				}
				return null;
			}).collect(toList());
			
			assertEquals(resultWithout, testList.stream()
					.map(Product::afterDiscounts)
					.map(Price::getValue)
					.reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
			
			test.addDiscounts(testList);
			
			assertEquals(resultWith, testList.stream()
					.map(Product::afterDiscounts)
					.map(Price::getValue)
					.reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
		} catch (DiscountInvalidException e1) {
			fail("The Bulk Discount could not be intitialized");
		} catch (NoSuchProductException e1) {
			fail("Unable to retrieve a product from the store");
		}
	}
}
