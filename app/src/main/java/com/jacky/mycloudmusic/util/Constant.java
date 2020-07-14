package com.jacky.mycloudmusic.util;

import com.jacky.mycloudmusic.BuildConfig;

public class Constant {
    /**
     * 端点
     */
    public static final String ENDPOINT = BuildConfig.ENDPOINT;

    public static final String GUIDE_IMAGE_ID = "GUIDE_IMAGE_ID";

    //Intent Key
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    public static final String TITLE = "TITLE";
    public static final String URL = "URL";

    //Intent Action
    public static final String ACTION_AD = "com.jacky.mycloudmusic.ACTION_AD";

    //fragmentTag
    public static final String LOGIN_FRAGMENT = "LOGIN_FRAGMENT";
    public static final String REGISTER_FRAGMENT = "REGISTER_FRAGMENT";
    public static final String WEB_VIEW_FRAGMENT = "WEB_VIEW_FRAGMENT";

    //fragmentTitle
    public static final String TITLE_LOGIN = "登录";
    public static final String TITLE_REGISTER = "注册";
    public static final String TITLE_ACTIVITY_DETAIL = "活动详情";
    public static final String TITLE_USER_AGREEMENT = "用户协议";

    /**
     * 手机号正则表达式
     * 移动：134 135 136 137 138 139 147 150 151 152 157 158 159 178 182 183 184 187 188 198
     * 联通：130 131 132 145 155 156 166 171 175 176 185 186
     * 电信：133 149 153 173 177 180 181 189 199
     * 虚拟运营商: 170
     */
    public static final String REGEX_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    /**
     * 邮箱正则表达式
     */
    public static final String REGEX_EMAIL = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
}
