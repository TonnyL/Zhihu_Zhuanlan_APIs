package com.marktony.zhuanlan.bean;

/**
 * Created by Lizhaotailang on 2016/10/1.
 */

public class ZhuanlanListItem {

    private boolean isTitleImageFullScreen;
    private String rating;
    private String sourceUrl;
    private String publishedTime;
    private Links links;
    private AuthorOrLiker author;
    private String url;
    private String title;
    private String titleImage;
    private String summary;
    private String content;
    private String state;
    private String href;
    private Meta meta;
    private String commentPermission;
    private String snapshotUrl;
    private boolean canComment;
    private int slug;
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

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(String publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public AuthorOrLiker getAuthor() {
        return author;
    }

    public void setAuthor(AuthorOrLiker author) {
        this.author = author;
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

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getCommentPermission() {
        return commentPermission;
    }

    public void setCommentPermission(String commentPermission) {
        this.commentPermission = commentPermission;
    }

    public String getSnapshotUrl() {
        return snapshotUrl;
    }

    public void setSnapshotUrl(String snapshotUrl) {
        this.snapshotUrl = snapshotUrl;
    }

    public boolean isCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

    public int getSlug() {
        return slug;
    }

    public void setSlug(int slug) {
        this.slug = slug;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
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

    public class Meta {

        private ZhuanlanListItem previous;
        private ZhuanlanListItem next;

        public ZhuanlanListItem getPrevious() {
            return previous;
        }

        public void setPrevious(ZhuanlanListItem previous) {
            this.previous = previous;
        }

        public ZhuanlanListItem getNext() {
            return next;
        }

        public void setNext(ZhuanlanListItem next) {
            this.next = next;
        }
    }

}
