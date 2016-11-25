package com.reddit.service;

import com.reddit.model.UserProfileEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserProfileService extends UserDetailsService{
    UserProfileEntity findByUser(String username);
    List<UserProfileEntity> findAll();
    UserProfileEntity save(UserProfileEntity persisted);
}
