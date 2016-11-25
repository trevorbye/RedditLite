package com.reddit.service;

import com.reddit.dao.RoleDao;
import com.reddit.model.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;


    @Override
    public RoleEntity findByRole(String role) {
        return roleDao.findByRole(role);
    }

    @Override
    public RoleEntity save(RoleEntity persisted) {
        return roleDao.save(persisted);
    }
}
