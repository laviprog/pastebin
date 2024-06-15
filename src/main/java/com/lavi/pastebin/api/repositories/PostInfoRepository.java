package com.lavi.pastebin.api.repositories;

import com.lavi.pastebin.api.models.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostInfoRepository extends JpaRepository<PostInfo, Long> {
    Optional<PostInfo> findByHash(String hash);
}
