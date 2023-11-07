package com.xszx.sparkweb.entity;

public class Top10Entity {
    private String viewCount;
    private String commitCount;
    private String shareCount;
    private String weiboId;

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(String commitCount) {
        this.commitCount = commitCount;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }
}
