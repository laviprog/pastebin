package com.lavi.pastebin.api.services;


import com.amazonaws.services.s3.model.S3Object;
import com.lavi.pastebin.api.models.Post;
import com.lavi.pastebin.api.models.PostInfo;
import com.lavi.pastebin.api.repositories.storage.YCStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private PostInfoService postRepositoryService;
    private YCStorage storage;

    public void save(Post post) {
        postRepositoryService.save(post.getPostInfo());
        storage.uploadObject(post.getPostInfo().getBucket(), post.getPostInfo().getKey(), post.getInputStream());
    }

    public void delete(Post post) {
        storage.deleteObject(post.getPostInfo().getBucket(), post.getPostInfo().getKey());
        postRepositoryService.delete(post.getPostInfo());
    }

    public void update(Post post) {
        postRepositoryService.update(post.getPostInfo());
        storage.updateObject(post.getPostInfo().getBucket(), post.getPostInfo().getKey(), post.getInputStream());
    }

    public Post get(String hash) {
        PostInfo postInfo = postRepositoryService.findByHash(hash);
        S3Object object = storage.getObject(postInfo.getBucket(), postInfo.getKey());
        // S3ObjectInputStream second param
        return new Post(postInfo, object.getObjectContent());
    }
}
