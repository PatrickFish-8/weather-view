package com.weather_view.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
// import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    private final RestClient restClient;
    
    public WeatherController(RestClient.Builder restClientBuilder, @Value("${weather.api}") String weatherEndpoint) {
        this.restClient = restClientBuilder.baseUrl(weatherEndpoint).build();
    }
    
    @GetMapping("/api/user_input")
    public Weather getWeather(@RequestParam("location") String location, @RequestParam("time") String time) {
        return this.restClient.get().uri("?latitude=42.36&longitude=-71.06&current=temperature_2m,weather_code&temperature_unit=fahrenheit").retrieve().body(Weather.class);
    }
}
