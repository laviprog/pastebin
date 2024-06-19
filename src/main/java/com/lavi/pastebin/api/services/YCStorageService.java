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

    public void delete(Post post){
        storage.deleteObject(post.getPostInfo().getBucket(), post.getPostInfo().getKey());
    }

    public void update(Post post){
        storage.updateObject(post.getPostInfo().getBucket(), post.getPostInfo().getKey(), post.getInputStream());
    }

    public S3Object getPost(PostInfo postInfo){
        return storage.getObject(postInfo.getBucket(), postInfo.getKey());
    }
}
