package dojo.supermarket.model;

public class Offer {
    private final SpecialOfferType offerType;
    private final double appleSauce;

    public Offer(SpecialOfferType offerType, double appleSauce) {
        this.offerType = offerType;
        this.appleSauce = appleSauce;
    }

    public SpecialOfferType getOfferType() {
        return offerType;
    }

    public double getAppleSauce() {
        return appleSauce;
    }
}
