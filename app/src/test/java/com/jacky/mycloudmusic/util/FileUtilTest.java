package com.jacky.mycloudmusic.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FileUtilTest {
    /**
     * 测试文件大小格式化
     */
    @Test
    public void testFormatFileSize() {
        //第一个参数等于第二个参数
        assertEquals(FileUtil.formatFileSize(1), "1.00Byte");

        //第一个参数等于第二个参数
        //为什么不等于1.23呢？
        //其实是因为单位换算的时候除以的是1024
        //1234/1024=1.205078125
        //可以看到格式化的时候四舍五入了
        assertEquals(FileUtil.formatFileSize(1234), "1.21K");

        //第一个参数不等于第二个参数
        assertNotEquals(FileUtil.formatFileSize(1234), "1K");

        assertEquals(FileUtil.formatFileSize(1024*1024), "1.00M");

        assertEquals(FileUtil.formatFileSize(1024*1024*1024L), "1.00G");

        assertEquals(FileUtil.formatFileSize(1024*1024*1024*1024L), "1.00T");

        assertEquals(FileUtil.formatFileSize(0), "0K");
    }
}
