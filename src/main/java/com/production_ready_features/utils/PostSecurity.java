package com.production_ready_features.utils;

import com.production_ready_features.DTO.PostDTO;
import com.production_ready_features.entities.User;
import com.production_ready_features.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {
    private  final PostService postService;

    public boolean isOwnerOfPost(Long postId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(user.getId());
    }
}
