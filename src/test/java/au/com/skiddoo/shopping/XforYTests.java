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
import au.com.skiddoo.shopping.specials.DiscountInvalidException;
import au.com.skiddoo.shopping.specials.XforY;


@RunWith(Parameterized.class)
public class XforYTests {

	private static ProductStore store;
	
	@Parameter(0)
	public int X;
	
	@Parameter(1)
	public int Y;
	
	@Parameter(2)
	public List<String> products;
	
	@Parameter(3)
	public String toApply;
	
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
				3, 
				2,
				Arrays.asList("appletv","appletv","appletv", "hdmiadapter"), 
				"appletv",
				BigDecimal.valueOf(758.5), 
				BigDecimal.valueOf(549.0) 
			},
			{ 
				4, 
				2,
				Arrays.asList("appletv","appletv","appletv", "hdmiadapter"), 
				"appletv",
				BigDecimal.valueOf(758.5), 
				BigDecimal.valueOf(758.5) 
			},
			{ 
				4, 
				2,
				Arrays.asList("appletv","appletv","appletv","appletv", "hdmiadapter"), 
				"appletv",
				BigDecimal.valueOf(968.0), 
				BigDecimal.valueOf(549.0) 
			}
		});
		
	}
	
	@Test
	public void test() {
		try {
			XforY test = new XforY(store.getProduct(toApply), X, Y, false);
		
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
			fail("Initialization of XForY failed");
		} catch (NoSuchProductException e1) {
			fail("Unable to retrieve product from store");
		}
	}

}
