package com.weather_view.entrypoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather_view.weather.Weather;
import com.weather_view.weather.WeatherController;
import com.weather_view.location.Location;
import com.weather_view.location.LocationController;
import com.weather_view.image.ImageController;

@RestController
public class EntryController {
    
    @GetMapping("/api/entry")
    public static ResponseEntity<Object> getEntry(
        @RequestParam("city") String city,
        @RequestParam(value = "state", required=false) String state,
        @RequestParam("country") String country,
        @Value("${geolocater.key}") String locationKey,
        @Value("${gcloud.auth.access.token}") String authAccessToken
    ) {
        try {
            Location location = LocationController.getLocation(city, state, country, locationKey);
            String longitude = location.lon();
            String latitude = location.lat();
    
            Weather weather = WeatherController.getWeather(longitude, latitude);
    
            // accessing fields of weather object
            int weatherCode = weather.current().weather_code();
            String localTime = weather.current().time();
            String currWeather;
    
            // weather logic
            switch (weatherCode) {
                case 0:
                    currWeather = "clear sky";
                    break;
                case 1:
                    currWeather = "clouds clearing";
                    break;
                case 2:
                    currWeather = "no change";
                    break;
                case 3:
                    currWeather = "clouds forming";
                    break;
                case 4:
                    currWeather = "smoke";
                    break;
                case 5:
                    currWeather = "haze";
                    break;
                case 6:
                    currWeather = "dust in air";
                    break;
                case 7:
                    currWeather = "wind‑blown dust or sand";
                    break;
                case 8:
                    currWeather = "dust whirl";
                    break;
                case 9:
                    currWeather = "dust or sandstorm";
                    break;
                case 10:
                    currWeather = "mist";
                    break;
                case 11:
                    currWeather = "patchy fog";
                    break;
                case 12:
                    currWeather = "fog";
                    break;
                case 13:
                    currWeather = "lightning only";
                    break;
                case 14:
                    currWeather = "precipitation overhead";
                    break;
                case 15:
                    currWeather = "distant precipitation";
                    break;
                case 16:
                    currWeather = "nearby precipitation";
                    break;
                case 17:
                    currWeather = "thunderstorm no rain";
                    break;
                case 18:
                    currWeather = "squalls";
                    break;
                case 19:
                    currWeather = "tornado funnel";
                    break;
                case 20:
                    currWeather = "drizzle";
                    break;
                case 21:
                    currWeather = "rain";
                    break;
                case 22:
                    currWeather = "snow";
                    break;
                case 23:
                    currWeather = "rain and snow";
                    break;
                case 24:
                    currWeather = "freezing rain";
                    break;
                case 25:
                    currWeather = "rain showers";
                    break;
                case 26:
                    currWeather = "snow showers";
                    break;
                case 27:
                    currWeather = "hail showers";
                    break;
                case 28:
                    currWeather = "fog";
                    break;
                case 29:
                    currWeather = "thunderstorm";
                    break;
                case 30:
                    currWeather = "duststorm easing";
                    break;
                case 31:
                    currWeather = "duststorm steady";
                    break;
                case 32:
                    currWeather = "duststorm starting";
                    break;
                case 33:
                    currWeather = "heavy duststorm easing";
                    break;
                case 34:
                    currWeather = "heavy duststorm steady";
                    break;
                case 35:
                    currWeather = "heavy duststorm starting";
                    break;
                case 36:
                    currWeather = "light blowing snow";
                    break;
                case 37:
                    currWeather = "heavy drifting snow";
                    break;
                case 38:
                    currWeather = "high blowing snow";
                    break;
                case 39:
                    currWeather = "heavy drifting snow";
                    break;
                case 40:
                    currWeather = "distant fog";
                    break;
                case 41:
                    currWeather = "patchy fog";
                    break;
                case 42:
                    currWeather = "thinning fog";
                    break;
                case 43:
                    currWeather = "thick fog";
                    break;
                case 44:
                    currWeather = "steady fog";
                    break;
                case 45:
                    currWeather = "thick fog";
                    break;
                case 46:
                    currWeather = "fog thickening";
                    break;
                case 47:
                    currWeather = "thick fog";
                    break;
                case 48:
                    currWeather = "freezing fog";
                    break;
                case 49:
                    currWeather = "freezing fog";
                    break;
                case 50:
                    currWeather = "light drizzle";
                    break;
                case 51:
                    currWeather = "drizzle";
                    break;
                case 52:
                    currWeather = "moderate drizzle";
                    break;
                case 53:
                    currWeather = "drizzle";
                    break;
                case 54:
                    currWeather = "heavy drizzle";
                    break;
                case 55:
                    currWeather = "drizzle";
                    break;
                case 56:
                    currWeather = "light freezing drizzle";
                    break;
                case 57:
                    currWeather = "freezing drizzle";
                    break;
                case 58:
                    currWeather = "drizzle and rain";
                    break;
                case 59:
                    currWeather = "heavy drizzle and rain";
                    break;
                case 60:
                    currWeather = "light rain";
                    break;
                case 61:
                    currWeather = "rain";
                    break;
                case 62:
                    currWeather = "moderate rain";
                    break;
                case 63:
                    currWeather = "rain";
                    break;
                case 64:
                    currWeather = "heavy rain";
                    break;
                case 65:
                    currWeather = "rain";
                    break;
                case 66:
                    currWeather = "light freezing rain";
                    break;
                case 67:
                    currWeather = "freezing rain";
                    break;
                case 68:
                    currWeather = "light sleet";
                    break;
                case 69:
                    currWeather = "sleet";
                    break;
                case 70:
                    currWeather = "light snow";
                    break;
                case 71:
                    currWeather = "snow";
                    break;
                case 72:
                    currWeather = "moderate snow";
                    break;
                case 73:
                    currWeather = "snow";
                    break;
                case 74:
                    currWeather = "heavy snow";
                    break;
                case 75:
                    currWeather = "snow";
                    break;
                case 76:
                    currWeather = "diamond dust";
                    break;
                case 77:
                    currWeather = "snow grains";
                    break;
                case 78:
                    currWeather = "snow crystals";
                    break;
                case 79:
                    currWeather = "ice pellets";
                    break;
                case 80:
                    currWeather = "light rain showers";
                    break;
                case 81:
                    currWeather = "rain showers";
                    break;
                case 82:
                    currWeather = "heavy rain showers";
                    break;
                case 83:
                    currWeather = "light rain and snow showers";
                    break;
                case 84:
                    currWeather = "rain and snow showers";
                    break;
                case 85:
                    currWeather = "light snow showers";
                    break;
                case 86:
                    currWeather = "snow showers";
                    break;
                case 87:
                    currWeather = "light hail showers";
                    break;
                case 88:
                    currWeather = "hail showers";
                    break;
                case 89:
                    currWeather = "light hail";
                    break;
                case 90:
                    currWeather = "hail";
                    break;
                case 91:
                    currWeather = "light rain after thunder";
                    break;
                case 92:
                    currWeather = "rain";
                    break;
                case 93:
                    currWeather = "light snow or hail";
                    break;
                case 94:
                    currWeather = "snow or hail";
                    break;
                case 95:
                    currWeather = "thunderstorm no hail";
                    break;
                case 96:
                    currWeather = "thunderstorm with hail";
                    break;
                case 97:
                    currWeather = "heavy thunderstorm";
                    break;
                case 98:
                    currWeather = "thunderstorm and duststorm";
                    break;
                case 99:
                    currWeather = "severe thunderstorm with hail";
                    break;
                default:
                    currWeather = "unknown weather";
                    break;
            }
    
            // generate image using information
            ImageController.getImage(currWeather, localTime, city, state, country, authAccessToken);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            System.out.println("Exception thrown: " + e);
            return ResponseEntity.status(400).build();
        }
    }
}
