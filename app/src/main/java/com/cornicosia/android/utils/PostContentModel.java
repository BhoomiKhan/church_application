package com.cornicosia.android.utils;

public class PostContentModel {
    private String postTitle;
    String postDescription;
    private String postPublishDate;
    private String postAuthor;
    private String postImageLarge;
    private String postImageSmall;

    public PostContentModel(String postTitle, String postDescription, String postPublishDate, String postAuthor, String postImageLarge, String postImageSmall) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postPublishDate = postPublishDate;
        this.postAuthor = postAuthor;
        this.postImageLarge = postImageLarge;
        this.postImageSmall = postImageSmall;
    }


    public String getPostDescription() {
        return postDescription;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostPublishDate() {
        return postPublishDate;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public String getPostImageLarge() {
        return postImageLarge;
    }

    public String getPostImageSmall() {
        return postImageSmall;
    }
}