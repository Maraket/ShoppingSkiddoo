// Copyright 2015 Skiddoo Pty Ltd

package au.com.skiddoo.shopping;

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
import au.com.skiddoo.shopping.products.PricingRules;
import au.com.skiddoo.shopping.products.PricingRules_Impl;
import au.com.skiddoo.shopping.products.Product;
import au.com.skiddoo.shopping.products.ProductStore;
import au.com.skiddoo.shopping.products.ProductStore_Impl;
import au.com.skiddoo.shopping.specials.BulkDiscount;
import au.com.skiddoo.shopping.specials.DiscountInvalidException;
import au.com.skiddoo.shopping.specials.XGetY;
import au.com.skiddoo.shopping.specials.XforY;

/**
 * @author jaiew
 */

@RunWith(Parameterized.class)
public class CheckoutRegisterTests {	
	
	private static ProductStore store;
	private static PricingRules rules;
	
	@Parameter(0)
	public List<String> shoppingCart;
	
	@Parameter(1)
	public BigDecimal result;
	
	@Parameters
	public static Collection<Object[]> before() {
		store = new ProductStore_Impl();
		store.addProduct(new Product("ipad", "Super iPad", new Price(BigDecimal.valueOf(649.99))));
		store.addProduct(new Product("macbookpro", "MacBook Pro", new Price(BigDecimal.valueOf(1499.99))));
		store.addProduct(new Product("appletv", "Apple TV", new Price(BigDecimal.valueOf(209.50))));
		store.addProduct(new Product("hdmiadapter", "HDMI adapter", new Price(BigDecimal.valueOf(130))));

		rules = new PricingRules_Impl();
		try {
			rules.addSpecial(new XforY(store.getProduct("appletv"), 3, 2, false));
			rules.addSpecial(new XGetY(store.getProduct("macbookpro"), store.getProduct("hdmiadapter"), false));
			rules.addSpecial(new BulkDiscount(store.getProduct("ipad"), 4, new Price(BigDecimal.valueOf(499.99)), false));
		} catch (DiscountInvalidException e) {
			fail("One of the special rules failed to initialize with known good values");
		} catch (NoSuchProductException e) {
			fail("Unable to retrieve product");
		}
		
		return Arrays.asList(new Object[][] {
			{
				Arrays.asList("appletv", "appletv", "appletv", "hdmiadapter"),
				BigDecimal.valueOf(549.00)
			},{
				Arrays.asList("appletv", "ipad", "ipad","appletv", "ipad", "ipad", "ipad"),
				BigDecimal.valueOf(2918.95)
			},{
				Arrays.asList("macbookpro", "hdmiadapter", "ipad"),
				BigDecimal.valueOf(2149.98)
			}
		});
		
	}
	
    @Test
    public void examplesProvided() {
		CheckoutRegister co = new Checkout(rules, store);
		
		shoppingCart.stream().forEach(sku -> {
			try {
				co.read(sku);
			} catch (NoSuchProductException e) {
				fail("Unable to read all elements from the shoppingCart");
			}
		});
		
		assertEquals(result, co.total().getValue());
    }
    
    

}
