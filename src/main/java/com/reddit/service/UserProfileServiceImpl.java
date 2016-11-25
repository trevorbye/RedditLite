package com.reddit.service;

import com.reddit.dao.UserProfileDao;
import com.reddit.model.RoleEntity;
import com.reddit.model.UserProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserProfileServiceImpl implements UserProfileService{
    @Autowired
    private UserProfileDao userProfileDao;


    @Override
    public UserProfileEntity findByUser(String username) {
        return userProfileDao.findByUsername(username);
    }

    @Override
    public List<UserProfileEntity> findAll() {
        return userProfileDao.findAll();
    }

    @Override
    public UserProfileEntity save(UserProfileEntity persisted) {
        return userProfileDao.save(persisted);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserProfileEntity userProfileEntity = userProfileDao.findByUsername(username);

        List<GrantedAuthority> authorities = buildUserAuthority(userProfileEntity.getRoles());
        return buildUserForAuthentication(userProfileEntity, authorities);
    }

    //converts UserProfileEntity to spring.User implementing UserDetails
    private User buildUserForAuthentication(UserProfileEntity userProfileEntity, List<GrantedAuthority> authorities) {
        return new User(userProfileEntity.getUsername(), userProfileEntity.getPassword(), userProfileEntity.getEnabled(),true,true,true,authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<RoleEntity> roles) {

        Set<GrantedAuthority> authoritySet = new HashSet<>();

        for (RoleEntity role : roles) {
            authoritySet.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> result = new ArrayList<>(authoritySet);

        return result;
    }
}
