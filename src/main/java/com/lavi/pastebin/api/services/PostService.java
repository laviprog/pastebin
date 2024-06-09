package com.lavi.pastebin.api.services;

import com.lavi.pastebin.api.models.Post;
import com.lavi.pastebin.api.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository repository;

    public boolean hasHash(final String hash) {
        return repository.findByHash(hash).isPresent();
    }

    public Post findByHash(final String hash) {
        return repository.findByHash(hash).orElse(null);
    }

    public Post save(final Post post) {
        return repository.save(post);
    }

    public Post update(final Post post) {
        return repository.save(post);
    }

    public void delete(final Post post) {
        repository.delete(post);
    }
}
