package com.iduck.sybin.toolkit.commonutils;

import com.iduck.sybin.toolkit.common.util.Judge;
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
public class JudgeTest {
    @Test
    public void isTrueTest() {
        Judge.isTrue(true).execute(() -> {
            System.out.println("true可以执行逻辑啦...");
        });
    }

    @Test
    public void isFalseTest() {
        Judge.isFalse(false).execute(() -> {
            System.out.println("false可以执行逻辑啦111...");
        });

        Judge.isFalse(false).execute(() -> {
            System.out.println("false可以执行逻辑啦222...");
        });
    }

    @Test
    public void eitherTest() {
        Judge.either(true).oneOf(() -> {

        }, () -> {

        });
    }
}
