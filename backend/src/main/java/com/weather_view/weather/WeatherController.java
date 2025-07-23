package com.weather_view.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class WeatherController {

    private final RestClient restClient;
    
    public WeatherController(RestClient.Builder restClientBuilder, @Value("${weather.api}") String weatherEndpoint) {
        this.restClient = restClientBuilder.baseUrl(weatherEndpoint).build();
    }
    
    @GetMapping("/api/weather")
    public String getWeather(
        @RequestParam("longitude") String longitude, 
        @RequestParam("latitude") String latitude
        ) {
        Weather response = this.restClient.get()
        .uri("?latitude={latitude}&longitude={longitude}&current=temperature_2m,weather_code&temperature_unit=fahrenheit&timezone=auto", latitude, longitude)
        .retrieve()
        .body(Weather.class);

        if (response != null) {
            int weatherCode = response.current().weather_code();

            // weather logic
            switch (weatherCode) {
                case 0:
                    return "clear sky";
                case 1:
                    return "clouds clearing";
                case 2:
                    return "no change";
                case 3:
                    return "clouds forming";
                case 4:
                    return "smoke";
                case 5:
                    return "haze";
                case 6:
                    return "dust in air";
                case 7:
                    return "wind‑blown dust or sand";
                case 8:
                    return "dust whirl";
                case 9:
                    return "dust or sandstorm";
                case 10:
                    return "mist";
                case 11:
                    return "patchy fog";
                case 12:
                    return "fog";
                case 13:
                    return "lightning only";
                case 14:
                    return "precipitation overhead";
                case 15:
                    return "distant precipitation";
                case 16:
                    return "nearby precipitation";
                case 17:
                    return "thunderstorm no rain";
                case 18:
                    return "squalls";
                case 19:
                    return "tornado funnel";
                case 20:
                    return "drizzle";
                case 21:
                    return "rain";
                case 22:
                    return "snow";
                case 23:
                    return "rain and snow";
                case 24:
                    return "freezing rain";
                case 25:
                    return "rain showers";
                case 26:
                    return "snow showers";
                case 27:
                    return "hail showers";
                case 28:
                    return "fog";
                case 29:
                    return "thunderstorm";
                case 30:
                    return "duststorm easing";
                case 31:
                    return "duststorm steady";
                case 32:
                    return "duststorm starting";
                case 33:
                    return "heavy duststorm easing";
                case 34:
                    return "heavy duststorm steady";
                case 35:
                    return "heavy duststorm starting";
                case 36:
                    return "light blowing snow";
                case 37:
                    return "heavy drifting snow";
                case 38:
                    return "high blowing snow";
                case 39:
                    return "heavy drifting snow";
                case 40:
                    return "distant fog";
                case 41:
                    return "patchy fog";
                case 42:
                    return "thinning fog";
                case 43:
                    return "thick fog";
                case 44:
                    return "steady fog";
                case 45:
                    return "thick fog";
                case 46:
                    return "fog thickening";
                case 47:
                    return "thick fog";
                case 48:
                    return "freezing fog";
                case 49:
                    return "freezing fog";
                case 50:
                    return "light drizzle";
                case 51:
                    return "drizzle";
                case 52:
                    return "moderate drizzle";
                case 53:
                    return "drizzle";
                case 54:
                    return "heavy drizzle";
                case 55:
                    return "drizzle";
                case 56:
                    return "light freezing drizzle";
                case 57:
                    return "freezing drizzle";
                case 58:
                    return "drizzle and rain";
                case 59:
                    return "heavy drizzle and rain";
                case 60:
                    return "light rain";
                case 61:
                    return "rain";
                case 62:
                    return "moderate rain";
                case 63:
                    return "rain";
                case 64:
                    return "heavy rain";
                case 65:
                    return "rain";
                case 66:
                    return "light freezing rain";
                case 67:
                    return "freezing rain";
                case 68:
                    return "light sleet";
                case 69:
                    return "sleet";
                case 70:
                    return "light snow";
                case 71:
                    return "snow";
                case 72:
                    return "moderate snow";
                case 73:
                    return "snow";
                case 74:
                    return "heavy snow";
                case 75:
                    return "snow";
                case 76:
                    return "diamond dust";
                case 77:
                    return "snow grains";
                case 78:
                    return "snow crystals";
                case 79:
                    return "ice pellets";
                case 80:
                    return "light rain showers";
                case 81:
                    return "rain showers";
                case 82:
                    return "heavy rain showers";
                case 83:
                    return "light rain and snow showers";
                case 84:
                    return "rain and snow showers";
                case 85:
                    return "light snow showers";
                case 86:
                    return "snow showers";
                case 87:
                    return "light hail showers";
                case 88:
                    return "hail showers";
                case 89:
                    return "light hail";
                case 90:
                    return "hail";
                case 91:
                    return "light rain after thunder";
                case 92:
                    return "rain";
                case 93:
                    return "light snow or hail";
                case 94:
                    return "snow or hail";
                case 95:
                    return "thunderstorm no hail";
                case 96:
                    return "thunderstorm with hail";
                case 97:
                    return "heavy thunderstorm";
                case 98:
                    return "thunderstorm and duststorm";
                case 99:
                    return "severe thunderstorm with hail";
                default:
                    return "unknown weather";
            }
        } else {
            throw new RuntimeException("Weather data not available");
        }


    }
}
