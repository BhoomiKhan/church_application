package com.cornicosia.android.utils;

public class PostContentModel {
    private String postTitle;
    private String postDescription;
    private String postPublishDate;
    private String postPublishTime;
    private String postAuthor;
    private String postImageLarge;
    private String postImageSmall;
    private String postPermalink;

    public PostContentModel(String postTitle, String postDescription, String postPublishDate, String postPublishTime, String postAuthor, String postImageLarge, String postImageSmall,String postPermalink) {
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postPublishDate = postPublishDate;
        this.postPublishTime = postPublishTime;
        this.postAuthor = postAuthor;
        this.postImageLarge = postImageLarge;
        this.postImageSmall = postImageSmall;
        this.postPermalink=postPermalink;
    }

    public String getPostPublishTime() {
        return postPublishTime;
    }

    public String getPostPermalink() {
        return postPermalink;
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