package com.lavi.pastebin.api.controllers;

import com.lavi.pastebin.api.models.Post;
import com.lavi.pastebin.api.services.PostService;
import com.lavi.pastebin.api.services.UserService;
import com.lavi.pastebin.generator.HashGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
public class MainController {
    private PostService postService;
    private UserService userService;
    private HashGenerator hashGenerator;

    @GetMapping("/")
    public String welcome(){
        return "Welcome to PasteBin!";
    }

    @GetMapping("/{hash}")
    public String getPost(@PathVariable String hash){
        Post post = postService.findByHash(hash);
        if (post == null) {
            log.error("Error, post missing!");
            return "Error, post missing!";
        }
        log.info(post.toString());
        return post.getBody();
    }

    @PutMapping("/create_post")
    public String createPost(@RequestBody Post post) {
        String hash = hashGenerator.getHash();
        post.setHash(hash);
        postService.save(post);
        return hash;
    }
}
