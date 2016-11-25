package com.reddit.dao;

import com.reddit.model.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<RoleEntity,Long> {
    RoleEntity findByRole(String role);

    @SuppressWarnings("unchecked")
    RoleEntity save(RoleEntity persisted);
}
