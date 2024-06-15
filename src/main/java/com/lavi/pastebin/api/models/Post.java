package com.lavi.pastebin.api.models;

import lombok.Data;

import java.io.InputStream;

@Data
public class Post {
    private PostInfo postInfo;
    private InputStream inputStream;

    public Post(PostInfo postInfo, InputStream inputStream) {
        this.postInfo = postInfo;
        this.inputStream = inputStream;
    }
}
