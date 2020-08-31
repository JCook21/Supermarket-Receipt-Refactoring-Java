package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.Test;

public class ReceiptPrinterTest {
    @Test
    public void cartWithNoOffersTest() {
        var catalog = new FakeCatalog();
        Product cheerios = new Product("Cheerios", ProductUnit.Each);
        Product cornflakes = new Product("Cornflakes", ProductUnit.Each);
        catalog.addProduct(cheerios, 1.99);
        catalog.addProduct(cornflakes, 2.56);
        var teller = new Teller(catalog);
        var cart = new ShoppingCart();
        cart.addItem(cheerios);
        cart.addItem(cornflakes);
        var receiptPrinter = new ReceiptPrinter();

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }
}
