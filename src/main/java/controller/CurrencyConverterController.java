package application;

import dao.CurrencyDao;
import entity.CurrencyConverter;
import entity.CurrencyType;
import view.CurrencyConverterView;

import java.util.ArrayList;

public class CurrencyConverterController {

    private ArrayList<CurrencyType> currencies;
    private CurrencyConverterView view;
    private CurrencyConverter converter = new CurrencyConverter();
    private CurrencyDao dao = new CurrencyDao();

    public CurrencyConverterController(CurrencyConverterView view) {
        this.view = view;
        getCurrencies();
    }

    public void getCurrencies() {
        ArrayList<String> currencies = dao.getAbbreviations();
        view.updateChoiceBoxes(currencies);
    }

    private boolean checkIfDouble(String input) {
        try {
            String string = input;
            if (input.indexOf(',') != -1) {
                System.out.println("Replacing...");
                string = string.replace(',','.');
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
            String fixed = amount.replace(',','.');
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
            }else if (amount == null || amount.isEmpty() || fixDouble(amount) <= 0) {
                throw new Exception("Given amount must be a positive number.");
            } else {
                double convertedAmount = converter.convert(dao.getRate(t1), dao.getRate(t2), fixDouble(amount));
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
