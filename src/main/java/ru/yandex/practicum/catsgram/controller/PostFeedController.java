package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostFeedController {
    private final PostService postService;

    @Autowired
    PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/feed/friends")
    @ResponseBody
    public List<Post> getFriendsPosts(@RequestParam String friends, @RequestParam(required = false, defaultValue = "10") Integer size, @RequestParam(required = false, defaultValue = "desc") String sort, @RequestParam(required = false, defaultValue = "0") Integer page) {
        return postService.getAllFriendsPosts(friends, size, sort, page);
    }
}