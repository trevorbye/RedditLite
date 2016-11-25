package com.reddit.service;

import com.reddit.dao.PostDao;
import com.reddit.model.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;


    @Override
    public long countOfPosts() {
        return postDao.count();
    }

    @Override
    public Page<PostEntity> getPage(int page) {
        Page<PostEntity> page1 = postDao.findAll(new PageRequest(page, 20));
        return page1;
    }

    @Override
    public PostEntity createPost(PostEntity persisted) {

        return postDao.save(persisted);
    }

    @Override
    public PostEntity findPostById(int id) {
        return postDao.findOne(id);
    }

    @Override
    public List<PostEntity> topPostsByUser(String username) {
        return postDao.findByUsernameOrderByLikesDesc(username);
    }
}
