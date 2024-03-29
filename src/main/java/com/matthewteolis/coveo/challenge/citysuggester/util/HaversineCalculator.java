package com.matthewteolis.coveo.challenge.citysuggester.util;

import org.springframework.stereotype.Component;

@Component
public class HaversineCalculator {

    /**
     * Calculate distance between two points in latitude and longitude taking into account height difference.
     * If you are not interested in height difference pass 0.0.
     * Uses Haversine method as its base.
     *
     * @param lat1 The latitude for the first position.
     * @param lon1 The longitude for the first position.
     * @param el1 The elevation for the first position.
     * @param lat2 The latitude for the second position.
     * @param lon2 The longitude for the second position.
     * @param el2 The elevation for the second position.
     * @return Distance in meters.
     * @see <a href="https://stackoverflow.com/a/16794680/3681702">source</a>
     */
    public double distance(double lat1, double lon1, double el1, double lat2, double lon2, double el2) {
        final double R = 6378.1; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
