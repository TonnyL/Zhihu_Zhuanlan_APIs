package com.marktony.zhuanlan.bean;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class PostItem {

    private String author;
    private String commentCount;
    private String imgUrl;
    private String title;
    private String briefContent;

    public PostItem(String author,String commentCount,String imgUrl,String title,String briefContent){
        this.author = author;
        this.commentCount = commentCount;
        this.imgUrl = imgUrl;
        this.title = title;
        this.briefContent = briefContent;
    }

    public String getAuthor() {
        return author;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }
}
