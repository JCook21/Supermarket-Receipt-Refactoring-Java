package dojo.supermarket.model;

public class Offer {
    private SpecialOfferType offerType;
    double argument;

    public Offer(SpecialOfferType offerType, double argument) {
        this.offerType = offerType;
        this.argument = argument;
    }

    public SpecialOfferType getOfferType() {
        return offerType;
    }
}
