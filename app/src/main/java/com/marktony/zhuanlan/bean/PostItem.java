package com.marktony.zhuanlan.bean;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class PostItem {

    private String author;
    private String commentCount;
    private String imgUrl = null;
    private String title;
    private String likeCount;
    private String slug;

    public PostItem(String slug,String author,String commentCount,String imgUrl,String title,String likeCount){
        this.slug = slug;
        this.author = author;
        this.commentCount = commentCount;
        this.imgUrl = imgUrl;
        this.title = title;
        this.likeCount = likeCount;
    }

    public String getAuthor() {
        return author;
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

    public String getLikeCount() {
        return likeCount;
    }

    public String getSlug() {
        return slug;
    }
}
