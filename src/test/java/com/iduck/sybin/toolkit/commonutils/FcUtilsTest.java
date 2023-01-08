package com.iduck.sybin.toolkit.commonutils;

import com.iduck.sybin.toolkit.common.util.FcUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 函数接口工具测试类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/1/8
 **/
@SpringBootTest
public class FcUtilsTest {
    @Test
    public void isTrueTest() {
        FcUtils.isTrue(true).execute(() -> {
            System.out.println("true可以执行逻辑啦...");
        });
    }

    @Test
    public void isFalseTest() {
        FcUtils.isFalse(false).execute(() -> {
            System.out.println("false可以执行逻辑啦...");
        });
    }
}
