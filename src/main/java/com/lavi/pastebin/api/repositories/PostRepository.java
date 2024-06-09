package com.lavi.pastebin.api.repositories;

import com.lavi.pastebin.api.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByHash(String hash);
}
