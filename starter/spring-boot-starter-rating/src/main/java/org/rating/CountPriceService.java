package org.rating;

public interface CountPriceService {

    double calculatePrice(double size, double rating);
    double convertPriceTo(String currentCurrency, String currency, double price);


}
