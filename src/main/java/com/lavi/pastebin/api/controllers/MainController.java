package com.lavi.pastebin.api.controllers;

import com.lavi.pastebin.api.models.Post;
import com.lavi.pastebin.api.models.PostInfo;
import com.lavi.pastebin.api.models.User;
import com.lavi.pastebin.api.repositories.storage.StorageConfig;
import com.lavi.pastebin.api.services.*;
import com.lavi.pastebin.generator.HashGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Controller
@Slf4j
@AllArgsConstructor
public class MainController {
    private PostService postService;
    private HashGenerator hashGenerator;
    private UserService userService;
    private YCStorageService storageService;
    private PostInfoService postInfoService;


    @GetMapping("/")
    public String empty() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("user", userDetails);
        return "index";
    }

    @GetMapping("/{hash}")
    public String getPost(@PathVariable String hash, Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Post post = postService.get(hash);
        if (userDetails == null || !userDetails.user().getUsername().equals(post.getPostInfo().getAuthor())) {
            post.getPostInfo().view();
            postInfoService.save(post.getPostInfo());
        }else{
            model.addAttribute("root", "author");
        }
        StringBuilder text = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(post.getInputStream()))) {
            bufferedReader.lines().forEach(x -> text.append(x).append("\n"));
        }
        model.addAttribute("text", text.toString());
        model.addAttribute("postInfo", post.getPostInfo());
        return "post";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String hash, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deleteByHash(hash);
        userDetails.user().getPosts().removeIf(post -> post.getHash().equals(hash));
        return "redirect:/welcome";
    }

    @PostMapping("/add_post")
    public String addPost(@RequestParam String text,
                          @RequestParam(required = false, defaultValue = "untitled") String title,
                          @RequestParam(required = false, defaultValue = "txt") String extension,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String hash = hashGenerator.getHash();
        Post post = new Post(
                new PostInfo(
                        hash,
                        extension,
                        StorageConfig.bucket,
                        title,
                        userDetails == null ? "anonymous" : userDetails.getUsername()
                ),
                new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))
        );
        if (userDetails != null) {
            User user = userDetails.user();
            user.getPosts().add(post.getPostInfo());
            log.info("post: {}", post.getPostInfo());
            log.info("user: {}", user);
            userService.update(user);
            storageService.save(post);
            log.info("save success");
        } else {
            postService.save(post);
        }
        return "redirect:/" + hash;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("message", null);
        return "login";
    }


    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User user, Model model) {
        Optional<User> optionalUser = userService.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            model.addAttribute("user", new User());
            model.addAttribute("error", "Invalid email address");
            return "register";
        }
        userService.save(user);
        model.addAttribute("message", "Регистрация успешна!");
        return "redirect:/login";
    }//TODO make to automatically authorization after registration
}
