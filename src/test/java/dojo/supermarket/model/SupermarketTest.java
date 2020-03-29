package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupermarketTest {

    private ShoppingCart cart;
    private Teller teller;
    private ReceiptPrinter receiptPrinter;
    private SupermarketCatalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new FakeCatalog();
        cart = new ShoppingCart();
        teller = new Teller(catalog);
        receiptPrinter = new ReceiptPrinter();
    }

    @Test
    void tenPercentDiscount() {
        var rice = new Product("rice", ProductUnit.Each);
        catalog.addProduct(rice, 2.49);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, rice, 10.0);
        cart.addItem(rice);

        var result = receiptPrinter.printReceipt(teller.checksOutArticlesFrom(cart));

        Approvals.verify(result);
    }

    @Test
    void twentyPercentDiscount() {
        var apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 20.0);
        cart.addItemQuantity(apples, 2.5);

        var result = receiptPrinter.printReceipt(teller.checksOutArticlesFrom(cart));

        Approvals.verify(result);
    }

    @Test
    void twoForSpecialPrice() {
        var cherryTomatoes = new Product("cherry tomatoes", ProductUnit.Each);
        catalog.addProduct(cherryTomatoes, 0.69);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, cherryTomatoes, 0.99);
        cart.addItemQuantity(cherryTomatoes, 2);

        var result = receiptPrinter.printReceipt(teller.checksOutArticlesFrom(cart));

        Approvals.verify(result);
    }

    @Test
    void fiveForSpecialPrice() {
        var toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste, 1.79);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste, 7.49);
        cart.addItemQuantity(toothpaste, 5);

        var result = receiptPrinter.printReceipt(teller.checksOutArticlesFrom(cart));

        Approvals.verify(result);
    }

    @Test
    void twoForOne() {
        var toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, toothbrush, 0.99);
        cart.addItemQuantity(toothbrush, 2);

        var result = receiptPrinter.printReceipt(teller.checksOutArticlesFrom(cart));

        Approvals.verify(result);
    }

    @Test
    void threeForTwo() {
        var toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 0.99);
        cart.addItemQuantity(toothbrush, 3);

        var result = receiptPrinter.printReceipt(teller.checksOutArticlesFrom(cart));

        Approvals.verify(result);
    }
}
