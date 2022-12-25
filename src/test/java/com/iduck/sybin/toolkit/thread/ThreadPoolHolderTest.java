package com.iduck.sybin.toolkit.thread;

import com.iduck.sybin.toolkit.thread.util.ThreadPoolHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/24
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ThreadPoolHolderTest {

    @Autowired
    ThreadPoolHolder threadPoolHolder;

    @Test
    public void test01() {
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            threadPoolHolder.execute(() -> {
                System.out.println(finalI);
            });
        }
    }
}
