package com.lavi.pastebin.api.models;

import com.lavi.pastebin.api.repositories.storage.StorageConfig;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posts_info")
@Data
public class PostInfo extends Model {
    private String hash;
    private String extension;
    private String bucket;
    private String key;
    private String title;
    private String author;

    public PostInfo(String hash) {
        this.hash = hash;
        this.bucket = StorageConfig.bucket;
        this.extension = "txt";
        this.key = hash + "." + extension;
        this.title = "untitled";
        this.author = "anonymous";
    }

    public PostInfo(String hash, String extension, String bucket, String title, String author) {
        this.hash = hash;
        this.extension = extension;
        this.bucket = bucket;
        this.key = hash + "." + extension;
        this.title = title;
        this.author = author;
    }

    public PostInfo() {
    }
}
