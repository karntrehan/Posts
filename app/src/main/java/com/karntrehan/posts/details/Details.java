package com.karntrehan.posts.details;

/**
 * Created by karn on 03-06-2017.
 */

public class Details {
    String userName;
    String userAvatar;
    long commentCount;

    public Details() {
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public void setUserAvatarFromEmail(String email) {
        this.userAvatar = "https://api.adorable.io/avatars/56/" + email + "@adorable.png";
    }
}
