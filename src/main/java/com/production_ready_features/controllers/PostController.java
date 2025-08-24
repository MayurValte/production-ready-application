package com.production_ready_features.controllers;

import com.production_ready_features.DTO.PostDTO;
import com.production_ready_features.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/getAllPosts")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/getPostById/{postId}")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")
    public PostDTO getPostById(@PathVariable(name = "postId") Long postId){
        return postService.getPostById(postId);
    }

    @PostMapping("/createNewPost")
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @PutMapping("/updatePost/{postId}")
    public PostDTO updatePost(@RequestBody PostDTO postDTO,@PathVariable(name = "postId") Long postId){
        return postService.updatePosts(postDTO,postId);
    }
}
