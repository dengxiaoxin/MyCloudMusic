package com.jacky.mycloudmusic.util;

import com.jacky.mycloudmusic.BuildConfig;

public class Constant {
    /**
     * 服务器API端点
     */
    public static final String ENDPOINT = BuildConfig.ENDPOINT;

    /**
     * 资源端点
     */
    public static final String RESOURCE_ENDPOINT = BuildConfig.RESOURCE_ENDPOINT;

    //Key
    public static final String GUIDE_IMAGE_ID = "GUIDE_IMAGE_ID";
    public static final String NICKNAME = "NICKNAME";

    //Intent Key
    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";
    public static final String TITLE = "TITLE";
    public static final String URL = "URL";
    public static final String ID = "COMMON_ID";
    public static final String SHEET_ID = "SHEET_ID";

    //Intent Action
    public static final String ACTION_AD = "com.jacky.mycloudmusic.ACTION_AD";

    //fragmentTag
    public static final String LOGIN_FRAGMENT = "LOGIN_FRAGMENT";
    public static final String REGISTER_FRAGMENT = "REGISTER_FRAGMENT";
    public static final String WEB_VIEW_FRAGMENT = "WEB_VIEW_FRAGMENT";
    public static final String SHEET_DETAIL_FRAGMENT = "SHEET_DETAIL_FRAGMENT";
    public static final String COMMENT_FRAGMENT = "COMMENT_FRAGMENT";
    public static final String USER_DETAIL_FRAGMENT = "USER_DETAIL_FRAGMENT";
    public static final String SIMPLE_PLAYER_FRAGMENT = "SIMPLE_PLAYER_FRAGMENT";

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

    //RecyclerView的ItemType
    /**
     * 标题
     */
    public static final int TYPE_TITLE = 0;

    /**
     * 歌单
     */
    public static final int TYPE_SHEET = 1;

    /**
     * 歌曲
     */
    public static final int TYPE_SONG = 2;

    /**
     * 计时器tick时间间隔：毫秒
     */
    //16毫秒
    //为什么是16毫秒？
    //因为后面我们要实现卡拉OK歌词
    //为了画面的连贯性
    //应该保持1秒60帧
    //所以1/60；就是一帧时间
    //如果没有卡拉OK歌词
    //那么每秒钟刷新一次即可
    public static final int DEFAULT_TIME = 16;

    /**
     * 消息编号
     */
    public static final int MESSAGE_PROGRESS = 0;
}
