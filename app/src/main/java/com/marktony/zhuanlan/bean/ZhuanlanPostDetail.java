package com.marktony.zhuanlan.bean;

import java.util.ArrayList;

/**
 * Created by Lizhaotailang on 2016/10/2.
 */

public class ZhuanlanPostDetail {

    private boolean isTitleImageFullScreen;
    private String rating;
    private String titleImage;
    private Links links;
    private ArrayList<AuthorOrLiker> reviewers;
    private ArrayList<Topic> topics;
    private TitleImageSize titleImageSize;
    private String href;
    private String excerptTitle;
    private AuthorOrLiker author;
    private Column column;
    private String tipjarState;
    private String content;
    private String state;
    private String sourceUrl;
    private int pageCommentsCount;
    private boolean canComment;
    private String snapshotUrl;
    private int slug;
    private String publishedTime;
    private String url;
    private String title;
    private ArrayList<AuthorOrLiker> lastestLikers;
    private String summary;
    private int reviewingCommentsCount;
    private Meta meta;
    private String commentPermission;

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getCommentPermission() {
        return commentPermission;
    }

    public void setCommentPermission(String commentPermission) {
        this.commentPermission = commentPermission;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    private int commentsCount;
    private int likesCount;

    public boolean isTitleImageFullScreen() {
        return isTitleImageFullScreen;
    }

    public void setTitleImageFullScreen(boolean titleImageFullScreen) {
        isTitleImageFullScreen = titleImageFullScreen;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public ArrayList<AuthorOrLiker> getReviewers() {
        return reviewers;
    }

    public void setReviewers(ArrayList<AuthorOrLiker> reviewers) {
        this.reviewers = reviewers;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public TitleImageSize getTitleImageSize() {
        return titleImageSize;
    }

    public void setTitleImageSize(TitleImageSize titleImageSize) {
        this.titleImageSize = titleImageSize;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getExcerptTitle() {
        return excerptTitle;
    }

    public void setExcerptTitle(String excerptTitle) {
        this.excerptTitle = excerptTitle;
    }

    public AuthorOrLiker getAuthor() {
        return author;
    }

    public void setAuthor(AuthorOrLiker author) {
        this.author = author;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getTipjarState() {
        return tipjarState;
    }

    public void setTipjarState(String tipjarState) {
        this.tipjarState = tipjarState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getPageCommentsCount() {
        return pageCommentsCount;
    }

    public void setPageCommentsCount(int pageCommentsCount) {
        this.pageCommentsCount = pageCommentsCount;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public int getSlug() {
        return slug;
    }

    public void setSlug(int slug) {
        this.slug = slug;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<AuthorOrLiker> getLastestLikers() {
        return lastestLikers;
    }

    public void setLastestLikers(ArrayList<AuthorOrLiker> lastestLikers) {
        this.lastestLikers = lastestLikers;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getReviewingCommentsCount() {
        return reviewingCommentsCount;
    }

    public void setReviewingCommentsCount(int reviewingCommentsCount) {
        this.reviewingCommentsCount = reviewingCommentsCount;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public class Links {

        private String comments;

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

    }

    public class TitleImageSize {

        private int width;
        private int height;

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    public class Column {

        private String slug;
        private String name;

        public String getName() {
            return name;
        }

        public String getSlug() {
            return slug;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }
    }

    public class Meta {

        private ZhuanlanPostDetail previous;
        private ZhuanlanPostDetail next;

        public ZhuanlanPostDetail getNext() {
            return next;
        }

        public ZhuanlanPostDetail getPrevious() {
            return previous;
        }

        public void setNext(ZhuanlanPostDetail next) {
            this.next = next;
        }

        public void setPrevious(ZhuanlanPostDetail previous) {
            this.previous = previous;
        }
    }

}
