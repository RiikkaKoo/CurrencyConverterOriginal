package controller;

import model.CurrencyConverter;
import model.CurrencyType;
import view.CurrencyConverterView;

import java.util.ArrayList;

public class CurrencyConverterController {

    private ArrayList<CurrencyType> currencies = new ArrayList<>();
    private CurrencyConverterView view;
    private CurrencyConverter converter = new CurrencyConverter();

    public CurrencyConverterController(CurrencyConverterView view) {
        this.view = view;
        currencies.add(new CurrencyType("EUR", "Euro", 1.0));
        currencies.add(new CurrencyType("USD", "United States dollar", 0.8504));
        currencies.add(new CurrencyType("GBP", "British pound", 1.1459));
        currencies.add(new CurrencyType("JPY", "Japanese yen", 0.0057));
        currencies.add(new CurrencyType("KRW", "South Korean won", 0.0006));
        currencies.add(new CurrencyType("SEK", "Swedish krona", 0.0907));
        getCurrencies();
    }

    public void getCurrencies() {
        ArrayList<String> abbs = new ArrayList<>();
        for (CurrencyType ct : currencies) {
            abbs.add(ct.getAbbreviation());
        }
        view.updateChoiceBoxes(abbs);
    }

    private boolean checkIfDouble(String input) {
        try {
            String string = input;
            if (input.indexOf(',') != -1) {
                System.out.println("Replacing...");
                string = string.replace(',', '.');
            }
            System.out.println("Converting " + string);
            double convert = Double.parseDouble(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private double fixDouble(String amount) {
        if (amount.indexOf(',') != -1) {
            System.out.println("Replacing...");
            String fixed = amount.replace(',', '.');
            return Double.parseDouble(fixed);
        } else {
            return Double.parseDouble(amount);
        }
    }

    public void doTheConversion() {
        try {
            String amount = view.getAmount();
            String t1 = view.getFromCurrency();
            String t2 = view.getToCurrency();

            System.out.println("Amount: " + amount + ", t1: " + t1 + ", t2: " + t2);

            if (t1 == null || t2 == null) {
                throw new Exception("Select currencies.");
            } else if (!checkIfDouble(amount)) {
                throw new Exception("Incorrect amount input.");
            } else if (amount == null || amount.isEmpty() || fixDouble(amount) <= 0) {
                throw new Exception("Given amount must be a positive number.");
            } else {
                double rate1 = 0;
                double rate2 = 0;
                for (CurrencyType ct : currencies) {
                    if (ct.getAbbreviation().equals(t1)) {
                        rate1 = ct.getRateToEur();
                    }
                }
                for (CurrencyType ct2 : currencies) {
                    if (ct2.getAbbreviation().equals(t2)) {
                        rate2 = ct2.getRateToEur();
                    }
                }
                double convertedAmount = converter.convert(rate1, rate2, fixDouble(amount));
                String str = "%.3f";
                System.out.println(convertedAmount);
                String formatted = String.format(str, convertedAmount);
                view.setTextInField2(formatted);
            }
        } catch (Exception e) {
            e.printStackTrace();
            view.setTextInField2("");
            view.displayMessage(e.getMessage());
        }
    }
}
