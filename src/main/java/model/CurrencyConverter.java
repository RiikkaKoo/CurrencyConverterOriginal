package model;

import java.util.ArrayList;

public class CurrencyConverter {

    public CurrencyConverter() {

    }

    public double convert(double rate1, double rate2, double amount) {
        if (rate1 == rate2) {
            return amount;
        } else {
            return amount * (rate1/rate2);
        }
    }
}
