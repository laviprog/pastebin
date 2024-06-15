package com.lavi.pastebin.api.services;

import com.lavi.pastebin.api.models.PostInfo;
import com.lavi.pastebin.api.repositories.PostInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostInfoService {
    private PostInfoRepository repository;

    public boolean hasHash(final String hash) {
        return repository.findByHash(hash).isPresent();
    }

    public PostInfo findByHash(final String hash) {
        return repository.findByHash(hash).orElse(null);
    }

    public void save(final PostInfo post) {
        repository.save(post);
    }

    public void update(final PostInfo post) {
        repository.save(post);
    }

    public void delete(final PostInfo post) {
        repository.delete(post);
    }
}
