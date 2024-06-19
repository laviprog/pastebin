package com.lavi.pastebin.api.services;


import com.amazonaws.services.s3.model.S3Object;
import com.lavi.pastebin.api.models.Post;
import com.lavi.pastebin.api.models.PostInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private PostInfoService postRepositoryService;
    private YCStorageService storage;

    public void save(Post post) {
        postRepositoryService.save(post.getPostInfo());
        storage.save(post);
    }

    public void delete(Post post) {
        storage.delete(post);
        postRepositoryService.delete(post.getPostInfo());
    }

    public void update(Post post) {
        postRepositoryService.update(post.getPostInfo());
        storage.update(post);
    }

    public Post get(String hash) {
        PostInfo postInfo = postRepositoryService.findByHash(hash);
        S3Object object = storage.getPost(postInfo);
        // S3ObjectInputStream second param
        return new Post(postInfo, object.getObjectContent());
    }
}
