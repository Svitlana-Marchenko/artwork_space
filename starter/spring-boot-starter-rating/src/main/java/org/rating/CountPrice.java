package org.rating;

import org.rating.exception.CurrencyException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class CountPrice implements CountPriceService{

    private static double pricePerSquareMeter=100.0;
    private static String apiKey = "1365lhqahv1r42ilsl2kg9cupp6jjr08tj50mkjv73g6dnnea9hcp";

    public double calculatePrice(double size, double rating) {
        double basePrice = size * pricePerSquareMeter;
        if (rating < 3.0) {
            return basePrice * (1 - 0.1 * (5 - rating));
        } else {
            return basePrice * (1 + (rating - 3) * 0.1);
        }
    }

    public double convertPriceTo(String currentCurrency, String currency, double price) {
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = String.format("https://anyapi.io/api/v1/exchange/convert?apiKey=%s&base=%s&to=%s&amount=%f", apiKey, currentCurrency, currency, price);
        RequestEntity<Void> request = RequestEntity.get(resourceUrl)
                .accept(MediaType.APPLICATION_JSON).build();

        try {
            ParameterizedTypeReference<HashMap<String, String>> responseType = new ParameterizedTypeReference<>() {
            };
            Map<String, String> jsonDictionary = restTemplate.exchange(request, responseType).getBody();
            if (jsonDictionary.get("converted") == null)
                return -1;
            return Double.parseDouble(jsonDictionary.get("converted"));
        } catch (HttpClientErrorException e){
            throw new CurrencyException(e.getMessage());
        }
    }

}
