package com.weather_view.image;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class ImageController {

    private static RestClient restClient;

    public ImageController(RestClient.Builder restClientBuilder, @Value("${weather.api}") String weatherEndpoint) {
        restClient = restClientBuilder.baseUrl("").build();
    }
    
    public static void getImage(
            String weather,
            String time,
            String city,
            String state,
            String country,
            String authAccessToken
        ) {

        String prompt;
        if (state == null) {
            prompt = String.format("Generate an image depicting %s weather at %s in %s, %s.", weather, time, city, country);
        } else {
            prompt = String.format("Generate an image depicting %s weather at %s in %s, %s %s.", weather, time, city, state, country);
        }

        // Build the request payload
        Map<String, Object> request = new HashMap<>();
        Map<String, String> instance = new HashMap<>();
        instance.put("prompt", prompt);
        List<Map<String,String>> instances = new ArrayList<>();
        instances.add(instance);
        request.put("instances", instances);
        Map<String,Integer> parameters = new HashMap<>();
        parameters.put("sampleCount", 1);
        request.put("parameters", parameters);

        String url = "https://us-east1-aiplatform.googleapis.com/v1/projects/weather-view-466219/locations/us-east1/publishers/google/models/imagen-3.0-generate-002:predict";
        ResponseEntity<Image> responseEntity = restClient.post()
            .uri(url)
            .header("Authorization", "Bearer " + authAccessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(Image.class);

        // get body of responseEntity and cast to Image object
        Image response = responseEntity.getBody();

        if (response != null && !response.predictions().isEmpty()) {
            String base64Image = response.predictions().get(0).bytesBase64Encoded();
            // decode base64 image
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            Path imagePath = Paths.get("frontend/public/generated_image.png");
            try {
                // try to write value of imageBytes to imagePath
                Files.write(imagePath, imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new RuntimeException("No predictions found in the response.");
        }
    }
}
