package com.reddit.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String body;

    private String username;

    private int likes;

    private int cmmnts;

    private Date submit_date;

    @Transient
    private List<NodeEntity> nodeList = new ArrayList<>();


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<NodeEntity> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeEntity> nodeList) {
        this.nodeList = nodeList;
    }

    public int getId() {
        return id;
    }

    public PostEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostEntity)) return false;

        PostEntity that = (PostEntity) o;

        if (id != that.id) return false;
        if (likes != that.likes) return false;
        if (cmmnts != that.cmmnts) return false;
        if (!title.equals(that.title)) return false;
        if (!body.equals(that.body)) return false;
        if (!username.equals(that.username)) return false;
        if (!submit_date.equals(that.submit_date)) return false;
        return nodeList.equals(that.nodeList);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + body.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + likes;
        result = 31 * result + cmmnts;
        result = 31 * result + submit_date.hashCode();
        result = 31 * result + nodeList.hashCode();
        return result;
    }
}
