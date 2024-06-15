package com.lavi.pastebin.api.controllers;

import com.lavi.pastebin.api.models.Post;
import com.lavi.pastebin.api.models.PostInfo;
import com.lavi.pastebin.api.repositories.storage.StorageConfig;
import com.lavi.pastebin.api.repositories.storage.YCStorage;
import com.lavi.pastebin.api.services.PostService;
import com.lavi.pastebin.api.services.UserService;
import com.lavi.pastebin.generator.HashGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;


@Controller
@Slf4j
@AllArgsConstructor
public class MainController {
    private PostService postService;
    private UserService userService;
    private HashGenerator hashGenerator;


    @GetMapping("/")
    public String welcome() {
        return "index";
    }

    @GetMapping("/{hash}")
    public String getPost(@PathVariable String hash, Model model) {
        Post post = postService.get(hash);
        model.addAttribute("post", post);
        return "post";
    }

    @PostMapping("/add_post")
    public String addPost(@RequestParam String text,
                          @RequestParam(defaultValue = "untitled") String title,
                          @RequestParam(defaultValue = "txt") String extension) {
        String hash = hashGenerator.getHash();
        Post post = new Post(
                new PostInfo(hash,
                        extension,
                        StorageConfig.bucket,
                        title,
                        "anonymous"
                ),
                new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))
        );
        postService.save(post);
        return "redirect:/" + hash;
    }
}
