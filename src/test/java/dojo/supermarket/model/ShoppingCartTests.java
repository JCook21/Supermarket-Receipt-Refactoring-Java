package dojo.supermarket.model;

import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Test;

public class ShoppingCartTests
{
	private ShoppingCart shoppingCart;

	@Before
	public void setup()
	{
		shoppingCart = new ShoppingCart();
	}

	@Test
	public void addOneNewProduct()
	{
		Product newProduct = new Product("Lucky Charms", ProductUnit.Each);

		shoppingCart.addItemQuantity(newProduct, 1.0);

		Approvals.verify(shoppingCart.productQuantities());
	}
}
