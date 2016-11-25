package com.reddit.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nodes")
public class NodeEntity {

    private int parentId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int nodeId;

    private String body;

    private String username;

    private int likes;

    private int cmmnts;

    private Date submit_date;

    private int postId;

    @Transient
    private Boolean show;

    @Transient
    private String errorResponse;

    @Transient
    private List<NodeEntity> nodeList = new ArrayList<>();

    public int getNodeId() {
        return nodeId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getCmmnts() {
        return cmmnts;
    }

    public void setCmmnts(int cmmnts) {
        this.cmmnts = cmmnts;
    }

    public Date getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(Date submit_date) {
        this.submit_date = submit_date;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public List<NodeEntity> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeEntity> nodeList) {
        this.nodeList = nodeList;
    }

    public NodeEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeEntity)) return false;

        NodeEntity that = (NodeEntity) o;

        if (parentId != that.parentId) return false;
        if (nodeId != that.nodeId) return false;
        if (likes != that.likes) return false;
        if (cmmnts != that.cmmnts) return false;
        if (postId != that.postId) return false;
        if (!body.equals(that.body)) return false;
        if (!username.equals(that.username)) return false;
        if (!submit_date.equals(that.submit_date)) return false;
        return nodeList.equals(that.nodeList);

    }

    @Override
    public int hashCode() {
        int result = parentId;
        result = 31 * result + nodeId;
        result = 31 * result + body.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + likes;
        result = 31 * result + cmmnts;
        result = 31 * result + submit_date.hashCode();
        result = 31 * result + postId;
        result = 31 * result + nodeList.hashCode();
        return result;
    }
}
