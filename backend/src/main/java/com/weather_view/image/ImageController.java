package com.weather_view.image;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class ImageController {

    private final RestClient restClient;

    public ImageController(RestClient.Builder restClientBuilder, @Value("${weather.api}") String weatherEndpoint) {
        this.restClient = restClientBuilder.baseUrl("").build();
    }
    
    @GetMapping("/api/image")
    public void getImage(
            @RequestParam(value = "weather", required = false) String weather,
            @RequestParam(value = "time", required = false) String time,
            @RequestParam(value = "location", required = false) String location,
            @Value("${google.project.id}") String googleProjId,
            @Value("${gcloud.auth.access.token}") String authAccessToken) {

        // Create a prompt combining weather, time, and location
        // String prompt = String.format("Generate an image depicting %s weather at %s in %s.", weather, time, location);
        String prompt = "Generate an image depicting a man eating spaghetti.";

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
        ResponseEntity<String> response = this.restClient.post()
            .uri(url)
            .header("Authorization", "Bearer " + authAccessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(String.class);

    
        // Parse the JSON response
        String body = response.getBody();
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        Map<String, Object> map = mapper.readValue(body, java.util.Map.class);

        // Extract the base64 encoded image from the first prediction
        List predictions = (List) map.get("predictions");
        if (!predictions.isEmpty()) {
            Map firstPrediction = (Map) predictions.get(0);
            String base64Image = (String) firstPrediction.get("bytesBase64Encoded");

            // Decode the image
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);

            // Write the image bytes to a file (e.g., generated_image.png)
            Path imagePath = Paths.get("generated_image.png");
            Files.write(imagePath, imageBytes);

            System.out.println("Image saved to: " + imagePath.toAbsolutePath());
        } else {
            throw new RuntimeException("No predictions found in the response.");
        }
    }
}
