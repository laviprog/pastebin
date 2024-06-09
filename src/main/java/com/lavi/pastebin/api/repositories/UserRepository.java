package com.lavi.pastebin.api.repositories;

import com.lavi.pastebin.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
