package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.Before;
import org.junit.Ignore;
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
        cart.addItemQuantity(chicken, 2.0);
        cart.addItem(redMeat);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, redMeat, 10.0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }

    @Test
    //@Ignore
    // Need to fix bug with Set
    public void cartWithMultipleDiscounts() {
        Product toothbrush = new Product("Toothbrush", ProductUnit.Each);
        Product toothpaste = new Product("Toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);
        catalog.addProduct(toothbrush, 0.99);
        cart.addItemQuantity(toothbrush, 3);
        cart.addItemQuantity(toothpaste, 5);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 0.99);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste, 7.49);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }




    @Test
    public void cartWithThreeForTwoOfferDiscount() {
        Product toothbrush = new Product("Toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        cart.addItemQuantity(toothbrush, 3);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 0.99);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }

    @Test
    public void cartWithFiveForAmountDiscountAddedTwice() {
        Product toothpaste = new Product("Toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);
        cart.addItemQuantity(toothpaste, 3);
        cart.addItemQuantity(toothpaste, 2);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste, 7.49);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }

    @Test
    public void cartWithTwoForAmountOfferQuantityMoreThanTwo() {
        Product boxOfCherryTomatoes = new Product("Box of Cherry Tomatoes", ProductUnit.Each);
        catalog.addProduct(boxOfCherryTomatoes, 0.69);
        cart.addItemQuantity(boxOfCherryTomatoes, 3);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, boxOfCherryTomatoes, 0.99);

        Receipt receipt = teller.checksOutArticlesFrom(cart);
        String receiptString = receiptPrinter.printReceipt(receipt);

        Approvals.verify(receiptString);
    }


}
