package com.weather_view.location;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class LocationController {
    
    private final RestClient restClient;

    public LocationController(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("").build();
    }

    @GetMapping("/api/location")
    public Location getLocation(  @RequestParam("city") String city,
                                @RequestParam(value = "state", required = false) String state,
                                @RequestParam("country") String country,
                                @Value("${geolocater.key}") String locationKey
                                ) {
        String query = (state != null && !state.trim().isEmpty())
                ? String.format("%s,%s,%s", city, state, country)
                : String.format("%s,%s", city, country);
        String uri = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&appid=%s&limit=1", query, locationKey);
        Location[] locations = this.restClient.get()
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
