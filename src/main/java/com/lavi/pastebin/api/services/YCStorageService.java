package com.lavi.pastebin.api.services;

import com.amazonaws.services.s3.model.S3Object;
import com.lavi.pastebin.api.models.Post;
import com.lavi.pastebin.api.models.PostInfo;
import com.lavi.pastebin.api.repositories.storage.YCStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class YCStorageService {
    private YCStorage storage;

    public void save(Post post){
        storage.uploadObject(post.getPostInfo().getBucket(), post.getPostInfo().getKey(), post.getInputStream());
    }

    public void delete(PostInfo post){
        storage.deleteObject(post.getBucket(), post.getKey());
    }

    public S3Object getPost(PostInfo postInfo){
        return storage.getObject(postInfo.getBucket(), postInfo.getKey());
    }
}
