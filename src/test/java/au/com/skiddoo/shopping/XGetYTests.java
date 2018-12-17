package au.com.skiddoo.shopping;

import static org.junit.Assert.*;

import static java.util.stream.Collectors.toList;

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
import au.com.skiddoo.shopping.specials.XGetY;

@RunWith(Parameterized.class)
public class XGetYTests {

	private static ProductStore store;
	
	@Parameter(0)
	public String X;
	
	@Parameter(1)
	public String Y;
	
	@Parameter(2)
	public List<String> products;
	
	@Parameter(3)
	public BigDecimal resultWithout;

	@Parameter(4)
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
				"macbookpro", 
				"hdmiadapter",
				Arrays.asList("macbookpro","hdmiadapter","ipad"), 
				BigDecimal.valueOf(2279.98), 
				BigDecimal.valueOf(2149.98) 
			},
			{ 
				"macbookpro", 
				"hdmiadapter",
				Arrays.asList("macbookpro","hdmiadapter", "hdmiadapter","ipad"), 
				BigDecimal.valueOf(2409.98), 
				BigDecimal.valueOf(2279.98) 
			},
			{ 
				"macbookpro", 
				"hdmiadapter",
				Arrays.asList("macbookpro", "macbookpro","hdmiadapter", "hdmiadapter","ipad"), 
				BigDecimal.valueOf(3909.97), 
				BigDecimal.valueOf(3649.97) 
			},
			{ 
				"macbookpro", 
				"hdmiadapter",
				Arrays.asList("hdmiadapter", "hdmiadapter","ipad"), 
				BigDecimal.valueOf(909.99), 
				BigDecimal.valueOf(909.99) 
			},
			{ 
				"macbookpro", 
				"hdmiadapter",
				Arrays.asList("macbookpro", "macbookpro", "ipad"), 
				BigDecimal.valueOf(3649.97), 
				BigDecimal.valueOf(3649.97) 
			}
		});
		
	}
	
	@Test
	public void test() {
		XGetY test;
		try {
			test = new XGetY(store.getProduct(X), store.getProduct(Y), false);
		
			List<Product> testList = products.stream().map(x -> {
				try {
					return store.getProduct(x);
				} catch (NoSuchProductException e) {
					fail("A product could be recovered during mapping");
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
			} catch (NoSuchProductException e1) {
			fail("Products could not be retrieved from store");
		}
		
	}

}
