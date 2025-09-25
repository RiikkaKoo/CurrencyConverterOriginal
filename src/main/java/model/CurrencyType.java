package entity;

public class CurrencyType {

    private String abbrevation;
    private String name;
    private double rateToEur;

    public CurrencyType(String abbrevation, String name, double toEurRate) {
        this.abbrevation = abbrevation;
        this.name = name;
        this.rateToEur = toEurRate;
    }

    public double getRateToEur() {
        return this.rateToEur;
    }

    public String getAbbrevation() {
        return this.abbrevation;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.abbrevation;
    }


}
