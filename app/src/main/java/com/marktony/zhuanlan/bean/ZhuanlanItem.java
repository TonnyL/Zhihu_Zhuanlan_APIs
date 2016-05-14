package com.marktony.zhuanlan.bean;

/**
 * Created by lizhaotailang on 2016/5/13.
 */
public class ZhuanlanItem {

    private String name ;
    private String slug;
    private String focusCount;
    private String articleCount;
    private String intro;
    private String avatarUrl;

    public ZhuanlanItem(String name,String slug,String avatarUrl,String focusCount,String articleCount,String intro){
        this.name = name;
        this.slug = slug;
        this.avatarUrl = avatarUrl;
        this.focusCount = focusCount;
        this.articleCount = articleCount;
        this.intro = intro;
    }

    public String getArticleCount() {
        return articleCount;
    }

    public String getFocusCount() {
        return focusCount;
    }

    public String getSlug() {
        return slug;
    }

    public String getIntro() {
        return intro;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
