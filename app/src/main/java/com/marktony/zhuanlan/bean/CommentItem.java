package com.marktony.zhuanlan.bean;

/**
 * Created by lizhaotailang on 2016/5/19.
 */
public class CommentItem {

    private String avatarUrl;
    private String author;
    private String content;
    private String time;
    private String likes;

    public CommentItem(String avatarUrl,String author,String content,String time,String likes){
        this.avatarUrl = avatarUrl;
        this.author = author;
        this.content = content;
        this.time = time;
        this.likes = likes;
    }

    public String getTime() {
        return time;
    }

    public String getLikes() {
        return likes;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
