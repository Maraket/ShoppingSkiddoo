// Copyright 2015 Skiddoo Pty Ltd

package au.com.skiddoo.shopping.products;

import java.util.List;

/**
 * @author jaiew
 */
public interface ProductStore {
    public Product getProduct(String productSku) throws NoSuchProductException;
    public ProductStore addProduct(Product product);
    public ProductStore addProducts(List<Product> products);
}
