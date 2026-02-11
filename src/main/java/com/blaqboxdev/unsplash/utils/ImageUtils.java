package com.blaqboxdev.unsplash.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageUtils {
    public static Path downloadImageToTemp(String imageUrl) throws Exception {
        Path tempFile = Files.createTempFile("pexels_", ".jpg");

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Important header!
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            System.out.println("Failed to download image: HTTP " + connection.getResponseCode());
            throw new RuntimeException("Failed to download image: HTTP " + connection.getResponseCode());
        }

        try (InputStream in = connection.getInputStream()) {
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e){
            System.out.println("failed to save to temp file");
            System.out.println(e);
            throw new Exception("Failed to download image");
        }

        return tempFile;
    }
}
