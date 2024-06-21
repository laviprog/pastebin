package com.lavi.pastebin.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDateTime created;
    private long numberViews;

    public PostInfo(String hash, String extension, String bucket, String title, String author) {
        this.hash = hash;
        this.extension = extension;
        this.bucket = bucket;
        this.key = hash + "." + extension;
        this.title = title;
        this.author = author;
        this.created = LocalDateTime.now();
        this.numberViews = 1;
    }

    public void view(){
        numberViews++;
    }

    public String getDateTimeOfCreation(){
        return created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public PostInfo() {
    }
}
