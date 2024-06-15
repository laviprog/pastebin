package com.lavi.pastebin.api.repositories.storage;

import com.amazonaws.auth.AWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig implements AWSCredentials {
    private final String accessKey;
    private final String secretKey;
    public static final String bucket = "lavi-pastebin";

    public StorageConfig(@Value("${AWS_ACCESS_KEY_ID}") String accessKey, @Value("${AWS_SECRET_KEY}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Override
    public String getAWSAccessKeyId() {
        return accessKey;
    }

    @Override
    public String getAWSSecretKey() {
        return secretKey;
    }

}
