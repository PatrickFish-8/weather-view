package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    
    @GetMapping("/api/user_input")
    public Location getLocation(@RequestParam("location") String location) {
        Location data = new Location("4.45", "5.5");
        return data;
    }
}
