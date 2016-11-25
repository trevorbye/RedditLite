package com.reddit.dao;

import com.reddit.model.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends PagingAndSortingRepository<PostEntity, Integer> {

    Page<PostEntity> findAll(Pageable pageable);

    long count();

    @SuppressWarnings("unchecked")
    PostEntity save(PostEntity persisted);

    PostEntity findOne(Integer id);

    List<PostEntity> findByUsernameOrderByLikesDesc(String username);
}
