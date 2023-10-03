package org.rating;

import java.util.Arrays;

public class CountRating implements CountRatingService{
    public double calculateRating(double[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0.0;
        }
        Arrays.sort(ratings);
        int n = ratings.length;
        double median;
        if (n % 2 == 0) {
            median = (ratings[n / 2 - 1] + ratings[n / 2]) / 2.0;
        } else {
            median = ratings[n / 2];
        }
        double mean = Arrays.stream(ratings).average().orElse(0.0);
        return (mean + median) / 2.0;

    }

}
