package com.lavi.pastebin.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posts")
@Data
public class Post extends Model {
    private String hash;
    private String title;
    private String body;
    private String author;
    private String tags;

    public Post(String body) {
        this.body = body;
    }

    public Post(String hash, String body) {
        this.hash = hash;
        this.body = body;
    }

    public Post() {

    }
}
