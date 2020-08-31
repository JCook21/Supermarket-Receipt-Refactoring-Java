package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Test;

public class ReceiptPrinterTest {

    private FakeCatalog catalog;
    private Teller teller;
    private ShoppingCart cart;
    private ReceiptPrinter receiptPrinter;

    @Before
    public void setUp() throws Exception {

        catalog = new FakeCatalog();
        teller = new Teller(catalog);
        cart = new ShoppingCart();
        receiptPrinter = new ReceiptPrinter();
    }

    @Test
    public void cartWithNoOffersTest() {
        Product cheerios = new Product("Cheerios", ProductUnit.Each);
        Product cornflakes = new Product("Cornflakes", ProductUnit.Each);
        catalog.addProduct(cornflakes, 2.56);
        catalog.addProduct(cheerios, 1.99);
        cart.addItem(cheerios);
        cart.addItem(cornflakes);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }

    @Test
    public void cartWithOneSpecialOffer()
    {
        Product redMeat = new Product("Red Meat", ProductUnit.Kilo);
        Product chicken = new Product("Chicken", ProductUnit.Kilo);
        catalog.addProduct(chicken, 2.56);
        catalog.addProduct(redMeat, 1.99);
        cart.addItem(redMeat);
        cart.addItem(chicken);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, redMeat, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }
}
