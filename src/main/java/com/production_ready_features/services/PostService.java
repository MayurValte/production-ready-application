package com.production_ready_features.services;

import com.production_ready_features.DTO.PostDTO;

import java.util.List;

public interface PostService {
    public List<PostDTO> getAllPosts();
    public PostDTO getPostById(Long id);
    public PostDTO createNewPost(PostDTO postDTO);

    PostDTO updatePosts(PostDTO postDTO, Long postId);
}
