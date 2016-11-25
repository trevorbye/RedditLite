package com.reddit.model;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private UserProfileEntity user;

    private String role;

    public UserProfileEntity getUser() {
        return user;
    }

    public void setUser(UserProfileEntity user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleEntity() {
    }
}
