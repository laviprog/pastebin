package com.lavi.pastebin.api.services;

import com.lavi.pastebin.api.models.User;
import com.lavi.pastebin.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public void delete(User user) {
        repository.delete(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }

    public void update(User user) {
        repository.save(user);
    }
}
