package com.home_app.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class WebcamController {

    private final Logger logger = LoggerFactory.getLogger(WebcamController.class);

    @Value("${capture.directory}")
    private String captureFolder;

    @GetMapping("/webcam")
    public String showWebcamPage() {
        return "webcam";
    }

    @PostMapping("/webcam/capture")
    public String captureImage(@RequestBody String requestBody) {
        // Generate a unique file name for the captured image
        String fileName = UUID.randomUUID().toString() + ".jpg";

        String imageData = extractBase64String(requestBody);

        // Save the captured image to the specified folder
        String filePath = captureFolder + fileName;
        saveImageToFile(imageData, filePath);
        logger.info("Saving image to {}", filePath);

        return "redirect:/webcam";
    }

    private void saveImageToFile(String imageData, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            // Remove the "data:image/png;base64," prefix from the image data
            String imageBase64 = imageData.replace("data:image/png;base64,", "");

            // Decode the base64-encoded image data and write it to the file
            byte[] imageBytes = java.util.Base64.getDecoder().decode(imageBase64);
            fos.write(imageBytes);
        } catch (IOException e) {
            logger.error("Error saving image to file", e);
        }
    }

    private String extractBase64String(String requestBody) {
        // Parse the JSON to extract the base64 string
        try {
            JSONObject json = new JSONObject(requestBody);
            return json.optString("imageData", "");
        } catch (JSONException e) {
            logger.error("Error parsing JSON request body", e);
            return "";
        }
    }

}
