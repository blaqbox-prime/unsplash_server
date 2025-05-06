package com.blaqboxdev.unsplash.Config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Value("${spring.cloud.azure.storage.blob.account-key}")
    String connectStr;

    @Bean
    BlobServiceClient blobServiceClient(){
        return new BlobServiceClientBuilder()
                .connectionString(connectStr)
                .buildClient();
    }
}
