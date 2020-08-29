package com.jacky.mycloudmusic.util;

/**
 * 资源工具类
 */
public class ResourceUtil {
    /**
     * 将相对资源转为绝对路径
     */
    public static String resourceUri(String uri) {
        if (!uri.startsWith("http"))
            uri = String.format(Constant.RESOURCE_ENDPOINT, uri);
        return uri;
    }
}
