package com.weather_view.location;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
    
@RestController
public class LocationController {
    
    private static RestClient restClient;

    public LocationController(RestClient.Builder restClientBuilder) {
        restClient = restClientBuilder.baseUrl("").build();
    }

    public static Location getLocation(
        String city,
        String state,
        String country,
        String locationKey
        ) {
        String query = (state != null && !state.trim().isEmpty())
                ? String.format("%s,%s,%s", city, state, country)
                : String.format("%s,%s", city, country);
        String uri = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s&limit=1", query, locationKey);
        Location[] locations = restClient.get()
            .uri(uri)
            .retrieve()
            .body(Location[].class);
        if (locations != null && locations.length != 0) {
            return locations[0];
        } else {
            throw new IllegalArgumentException("Location not found for the given query: " + query);
        }
    }
}
