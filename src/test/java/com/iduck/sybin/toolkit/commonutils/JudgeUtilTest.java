package com.iduck.sybin.toolkit.commonutils;

import com.iduck.sybin.toolkit.common.utils.JudgeUtil;
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
public class JudgeUtilTest {
    @Test
    public void isTrueTest() {
        JudgeUtil.isTrue(true).execute(() -> {
            System.out.println("true可以执行逻辑啦...");
        });
    }

    @Test
    public void isFalseTest() {
        JudgeUtil.isFalse(false).execute(() -> {
            System.out.println("false可以执行逻辑啦111...");
        });

        JudgeUtil.isFalse(false).execute(() -> {
            System.out.println("false可以执行逻辑啦222...");
        });
    }

    @Test
    public void eitherTest() {
        JudgeUtil.trueOrFalse(true).either(() -> {

        }, () -> {

        });
    }
}
