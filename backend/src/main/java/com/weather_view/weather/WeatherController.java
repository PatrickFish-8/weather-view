package com.weather_view.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class WeatherController {

    private static RestClient restClient;
    
    public WeatherController(RestClient.Builder restClientBuilder, @Value("${weather.api}") String weatherEndpoint) {
        restClient = restClientBuilder.baseUrl(weatherEndpoint).build();
    }

    public static Weather getWeather(
        @RequestParam("longitude") String longitude, 
        @RequestParam("latitude") String latitude
        ) {
        Weather response = restClient.get()
        .uri("?latitude={latitude}&longitude={longitude}&current=temperature_2m,weather_code&temperature_unit=fahrenheit&timezone=auto", latitude, longitude)
        .retrieve()
        .body(Weather.class);

        return response;
    }
}
