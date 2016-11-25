package com.reddit.service;


import com.reddit.model.PostEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    long countOfPosts();
    Page<PostEntity> getPage(int page);
    PostEntity createPost(PostEntity persisted);
    PostEntity findPostById(int id);
    List<PostEntity> topPostsByUser(String username);
}
