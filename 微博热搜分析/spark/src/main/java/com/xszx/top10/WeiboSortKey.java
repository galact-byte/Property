package com.xszx.top10;

import java.io.Serializable;

public class WeiboSortKey implements Comparable<WeiboSortKey>, Serializable {
    private int viewCount;
    private int shareCount;
    private int commitCount;

    public WeiboSortKey(
            int viewcount,
            int shareCount,
            int commitCount) {
        this.viewCount = viewcount;
        this.shareCount = shareCount;
        this.commitCount = commitCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int cartCount) {
        this.shareCount = cartCount;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }

    @Override
    public int compareTo(WeiboSortKey other) {
        if (viewCount - other.getViewCount() != 0) {
            return (int) (viewCount - other.getViewCount());
        } else if (shareCount - other.getShareCount() != 0) {
            return (int) (shareCount - other.getShareCount());
        } else if (commitCount - other.getCommitCount() != 0) {
            return (int) (commitCount - other.getCommitCount());
        }
        return 0;
    }
}
