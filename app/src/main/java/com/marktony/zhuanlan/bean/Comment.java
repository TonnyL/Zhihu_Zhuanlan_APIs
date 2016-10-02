package com.marktony.zhuanlan.bean;

/**
 * Created by Lizhaotailang on 2016/10/2.
 */

public class Comment {

    private String content;
    private boolean liked;
    private String href;
    private int inReplyToCommentId;
    private boolean reviewing;
    private AuthorOrLiker author;
    private String createdTime;
    private boolean featured;
    private int id;
    private int likesCount;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getInReplyToCommentId() {
        return inReplyToCommentId;
    }

    public void setInReplyToCommentId(int inReplyToCommentId) {
        this.inReplyToCommentId = inReplyToCommentId;
    }

    public boolean isReviewing() {
        return reviewing;
    }

    public void setReviewing(boolean reviewing) {
        this.reviewing = reviewing;
    }

    public AuthorOrLiker getAuthor() {
        return author;
    }

    public void setAuthor(AuthorOrLiker author) {
        this.author = author;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
