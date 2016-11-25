package com.reddit.service;


import com.reddit.model.RoleEntity;

public interface RoleService {
    RoleEntity findByRole(String role);
    RoleEntity save(RoleEntity persisted);
}
