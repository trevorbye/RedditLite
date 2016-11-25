package com.reddit.dao;

import com.reddit.model.UserProfileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileDao extends CrudRepository<UserProfileEntity, Long> {

    UserProfileEntity findByUsername(String username);

    List<UserProfileEntity> findAll();

    @SuppressWarnings("unchecked")
    UserProfileEntity save(UserProfileEntity persisted);
}
