package com.reddit.POJO;

public class NewPostResponse {

    public NewPostResponse() {
    }

    private String title;
    private String body;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewPostResponse)) return false;

        NewPostResponse that = (NewPostResponse) o;

        if (!title.equals(that.title)) return false;
        return body.equals(that.body);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}
