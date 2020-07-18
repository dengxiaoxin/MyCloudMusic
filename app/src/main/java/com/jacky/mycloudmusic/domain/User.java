package com.jacky.mycloudmusic.domain;

import android.text.TextUtils;

/**
 * 用户模型
 * 用来实现登录的时候传递参数
 * 因要向外传递，所以间接继承Serializable
 */
public class User extends BaseModel {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 描述
     */
    private String description;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 格式化后的描述
     */
    public String getDescriptionFormat() {
        if (TextUtils.isEmpty(description))
            return "这个人很懒，没有填写个人介绍！";

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
