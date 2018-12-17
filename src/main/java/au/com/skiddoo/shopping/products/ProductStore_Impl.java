package au.com.skiddoo.shopping.products;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductStore_Impl implements ProductStore {

	private Map<String, Product> productList; 
	
	public ProductStore_Impl() {
		productList = new HashMap<String, Product>();
	}
	
	public ProductStore_Impl(Map<String, Product> existingProducts) {
		this.productList = existingProducts;
	}
	
	@Override
	public Product getProduct(String productSku) throws NoSuchProductException{
		Product output = productList.get(productSku);
		if(output == null)
			throw new NoSuchProductException("Product requested doesn't exist");
		return new Product(output.getSku(), output.getName(), output.getPrice());
	}

	@Override
	public ProductStore addProduct(Product product) {
		productList.put(product.getSku(), product);
		return this;
	}

	@Override
	public ProductStore addProducts(List<Product> products) {
		products.stream()
			.forEach(elm -> productList.put(elm.getSku(), elm));
		return this;
	}

}
