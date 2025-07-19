package com.weather_view.weather;

public record Weather(double longitude, double latitude, Current current, String timezone) {
    public record Current(String time, double temperature_2m, int weather_code) { }
}