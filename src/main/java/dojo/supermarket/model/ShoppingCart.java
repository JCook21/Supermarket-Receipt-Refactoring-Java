package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new TreeMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product: productQuantities().keySet()) {
            double quantity = productQuantities.get(product);
            if (offers.containsKey(product)) {
                Offer offer = offers.get(product);
                double unitPrice = catalog.getUnitPrice(product);
                int quantityAsInt = (int) quantity;
                Discount discount = null;
                int offerQuantity = 1;
                if (offer.getOfferType() == SpecialOfferType.ThreeForTwo) {
                    offerQuantity = 3;

                } else if (offer.getOfferType() == SpecialOfferType.TwoForAmount) {
                    offerQuantity = 2;
                    if (quantityAsInt >= 2) {
                        double total = offer.getAppleSauce() * (quantityAsInt / offerQuantity) + quantityAsInt % 2 * unitPrice;
                        double discountN = unitPrice * quantity - total;
                        discount = new Discount(product, "2 for " + offer.getAppleSauce(), discountN);
                    }

                } if (offer.getOfferType() == SpecialOfferType.FiveForAmount) {
                    offerQuantity = 5;
                }
                int tacos = quantityAsInt / offerQuantity;
                if (isValidThreeForTwoOffer(offer, quantityAsInt)) {
                    double discountAmount = quantity * unitPrice - ((tacos * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                    discount = new Discount(product, "3 for 2", discountAmount);
                }
                if (offer.getOfferType() == SpecialOfferType.TenPercentDiscount) {
                    discount = new Discount(product, offer.getAppleSauce() + "% off", quantity * unitPrice * offer.getAppleSauce() / 100.0);
                }
                if (offer.getOfferType() == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
                    double discountTotal = unitPrice * quantity - (offer.getAppleSauce() * tacos + quantityAsInt % 5 * unitPrice);
                    discount = new Discount(product, offerQuantity + " for " + offer.getAppleSauce(), discountTotal);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    private boolean isValidThreeForTwoOffer(Offer offer, int quantityAsInt) {
        return offer.getOfferType() == SpecialOfferType.ThreeForTwo && quantityAsInt > 2;
    }
}
