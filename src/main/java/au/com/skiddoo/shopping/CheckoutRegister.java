// Copyright 2015 Skiddoo Pty Ltd

package au.com.skiddoo.shopping;

import au.com.skiddoo.shopping.products.NoSuchProductException;
import au.com.skiddoo.shopping.products.Price;

/**
 * @author jaiew
 */
public interface CheckoutRegister {
	CheckoutRegister read(String sku) throws NoSuchProductException; //This would allow chaining of the Register
    Price total();

}
