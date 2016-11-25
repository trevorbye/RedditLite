package com.reddit.model;

import javax.persistence.*;

@Entity
@Table(name = "likeTracker")
public class LikeTrackerEntity {

    private String username;

    private int nodeId;

    private int postId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public String getUsername() {
        return username;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getPostId() {
        return postId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public LikeTrackerEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeTrackerEntity)) return false;

        LikeTrackerEntity that = (LikeTrackerEntity) o;

        if (nodeId != that.nodeId) return false;
        if (postId != that.postId) return false;
        if (id != that.id) return false;
        return username.equals(that.username);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + nodeId;
        result = 31 * result + postId;
        result = 31 * result + id;
        return result;
    }
}
