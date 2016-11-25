package com.reddit.POJO;

/**
 * Created by trevorBye on 10/20/16.
 */
public class CommentResponse {
    private String comment;
    private int parentId;
    private int rootPostId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getRootPostId() {
        return rootPostId;
    }

    public void setRootPostId(int rootPostId) {
        this.rootPostId = rootPostId;
    }

    public CommentResponse() {
    }
}
