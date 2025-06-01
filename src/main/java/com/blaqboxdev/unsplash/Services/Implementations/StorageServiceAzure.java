package com.blaqboxdev.unsplash.Services.Implementations;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.blaqboxdev.unsplash.Services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class StorageServiceAzure implements StorageService {
    private BlobServiceClient blobServiceClient;

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String key;


    private BlobContainerClient getContainer(){
        System.out.println("connection-string = " + key);
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(key)
                .buildClient();
        blobServiceClient.createBlobContainerIfNotExists("photos");
        return blobServiceClient.getBlobContainerClient("photos");
    }


    public String uploadImage(String name, InputStream data) {
        BlobContainerClient photosContainer = getContainer();
        BlobClient blobClient = photosContainer.getBlobClient(name);
        blobClient.upload(data, true);
        return blobClient.getBlobUrl();
    }

    @Override
    public String uploadFile(File file) {
        System.out.println(file.getName());
        try {
            return this.uploadImage(file.getName(), file.toURL().openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
