package com.blaqboxdev.unsplash.Services;

import com.azure.storage.blob.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StorageService {

    final BlobServiceClient blobServiceClient;

    public StorageService() {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString("DefaultEndpointsProtocol=https;AccountName=unsplashstoragerepo;AccountKey=0nyAulsSNfG0gfpk5UZJKbioQoB/2WEoguydk6oQ5XAfDfBbPGNZvvMCvKTU80EL6U26eJpMAl8O+AStzcj90Q==;EndpointSuffix=core.windows.net")
                .buildClient();
    }

    private BlobContainerClient getContainer(){
        blobServiceClient.createBlobContainerIfNotExists("photos");
        return blobServiceClient.getBlobContainerClient("photos");
    }


    public String uploadImage(String name, InputStream data) {
        BlobContainerClient photosContainer = getContainer();
        BlobClient blobClient = photosContainer.getBlobClient(name);
        blobClient.upload(data, true);
        return blobClient.getBlobUrl();
    }
}
