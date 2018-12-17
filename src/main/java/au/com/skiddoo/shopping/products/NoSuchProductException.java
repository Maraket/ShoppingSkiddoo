package au.com.skiddoo.shopping.products;

public class NoSuchProductException extends Exception {
	public NoSuchProductException(String errorMessage) {
		super(errorMessage);
	}
}
