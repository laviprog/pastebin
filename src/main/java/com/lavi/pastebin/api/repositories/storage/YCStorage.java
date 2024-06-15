package com.lavi.pastebin.api.repositories.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
public class YCStorage {
    private final AmazonS3 s3Client;

    public YCStorage(StorageConfig storageConfig) {
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(storageConfig))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                "storage.yandexcloud.net","ru-central1"
                        )
                )
                .build();
    }

    public void uploadObject(String bucket, String key, InputStream inputStream) {
        s3Client.putObject(bucket, key, inputStream, new ObjectMetadata());
    }

    public S3Object getObject(String bucket, String key) {
        return s3Client.getObject(bucket, key);
    }

    public void deleteObject(String bucket, String key) {
        s3Client.deleteObject(bucket, key);
    }

    public void updateObject(String bucket, String key, InputStream inputStream) {
        s3Client.putObject(bucket, key, inputStream, new ObjectMetadata());
    }

}
