package com.weather_view.image;

import java.util.List;

public record Image(List<Prediction> predictions) {
    public record Prediction(String mimeType, String bytesBase64Encoded, String prompt) { }
}
