package com.marktony.zhuanlan.bean;

import java.util.ArrayList;

/**
 * Created by Lizhaotailang on 2016/9/30.
 */

public class Zhuanlan {

    private int followersCount;
    private AuthorOrLiker creator;
    private ArrayList<Topic> topics;
    private String activateState;
    private String href;
    private boolean acceptSubmission;
    private boolean firstTime;
    private ArrayList<Topic> postTopics;
    private String pendingName;
    private AuthorOrLiker.Avatar avatar;
    private boolean canManage;
    private String description;
    private ArrayList<Topic> pendingTopics;
    private int nameCanEditUntil;
    private String reason;
    private int banUntil;
    private String slug;
    private String name;
    private String url;
    private String intro;
    private int topicsCanEditUntil;
    private String activateAuthorRequested;
    private String commentPermission;
    private boolean following;
    private int postsCount;
    private boolean canPost;

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public AuthorOrLiker getCreator() {
        return creator;
    }

    public void setCreator(AuthorOrLiker creator) {
        this.creator = creator;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public String getActivateState() {
        return activateState;
    }

    public void setActivateState(String activateState) {
        this.activateState = activateState;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isAcceptSubmission() {
        return acceptSubmission;
    }

    public void setAcceptSubmission(boolean acceptSubmission) {
        this.acceptSubmission = acceptSubmission;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public ArrayList<Topic> getPostTopics() {
        return postTopics;
    }

    public void setPostTopics(ArrayList<Topic> postTopics) {
        this.postTopics = postTopics;
    }

    public String getPendingName() {
        return pendingName;
    }

    public void setPendingName(String pendingName) {
        this.pendingName = pendingName;
    }

    public AuthorOrLiker.Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(AuthorOrLiker.Avatar avatar) {
        this.avatar = avatar;
    }

    public boolean isCanManage() {
        return canManage;
    }

    public void setCanManage(boolean canManage) {
        this.canManage = canManage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Topic> getPendingTopics() {
        return pendingTopics;
    }

    public void setPendingTopics(ArrayList<Topic> pendingTopics) {
        this.pendingTopics = pendingTopics;
    }

    public int getNameCanEditUntil() {
        return nameCanEditUntil;
    }

    public void setNameCanEditUntil(int nameCanEditUntil) {
        this.nameCanEditUntil = nameCanEditUntil;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getBanUntil() {
        return banUntil;
    }

    public void setBanUntil(int banUntil) {
        this.banUntil = banUntil;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getTopicsCanEditUntil() {
        return topicsCanEditUntil;
    }

    public void setTopicsCanEditUntil(int topicsCanEditUntil) {
        this.topicsCanEditUntil = topicsCanEditUntil;
    }

    public String getActivateAuthorRequested() {
        return activateAuthorRequested;
    }

    public void setActivateAuthorRequested(String activateAuthorRequested) {
        this.activateAuthorRequested = activateAuthorRequested;
    }

    public String getCommentPermission() {
        return commentPermission;
    }

    public void setCommentPermission(String commentPermission) {
        this.commentPermission = commentPermission;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public int getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(int postsCount) {
        this.postsCount = postsCount;
    }

    public boolean isCanPost() {
        return canPost;
    }

    public void setCanPost(boolean canPost) {
        this.canPost = canPost;
    }

    public class Topic {

        private String url;
        private String id;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
