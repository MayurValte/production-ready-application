package com.production_ready_features.services;

import com.production_ready_features.DTO.PostDTO;
import com.production_ready_features.entities.PostEntity;
import com.production_ready_features.entities.User;
import com.production_ready_features.exceptions.ResourceNotFoundException;
import com.production_ready_features.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long id) {
        PostEntity postEntity = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not font with id" + id));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);
        postEntity.setAuthor(user);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO updatePosts(PostDTO inputPost, Long postId) {
        PostEntity olderPost=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId + " to update"));
        inputPost.setId(postId);
        modelMapper.map(inputPost, olderPost);
        PostEntity savedPostEntity = postRepository.save(olderPost);
        return modelMapper.map(savedPostEntity, PostDTO.class);
    }
}
