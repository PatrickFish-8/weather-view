package com.weather_view.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
// import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {

    @Value("${weather.api}")
    private String weatherEndpoint;
    private final RestClient restClient;

    public WeatherController(RestClient.Builder restClientBuilder) {
		this.restClient = restClientBuilder.baseUrl("https://api.open-meteo.com/v1/forecast").build();
	}
    
    @GetMapping("/api/user_input")
    public Weather getWeather(@RequestParam("location") String location, @RequestParam("time") String time) {
        return this.restClient.get().uri("?latitude=42.36&longitude=-71.06&current=temperature_2m,weather_code&temperature_unit=fahrenheit").retrieve().body(Weather.class);
    }
}
