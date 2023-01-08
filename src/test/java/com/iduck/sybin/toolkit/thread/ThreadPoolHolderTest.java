package com.iduck.sybin.toolkit.thread;

import com.iduck.sybin.toolkit.thread.bean.ThreadPoolHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
//        for (int i = 0; i < 100; i++) {
//            int finalI = i;
//            threadPoolHolder.execute(() -> {
//                System.out.println(finalI);
//            });
//        }
        System.out.println("execute执行完毕!");

        ArrayList<Future<Integer>> futureList = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Future<Integer> submit = threadPoolHolder.submit(() -> {
                if (finalI == 1) {
                    Thread.sleep(20000);
                }
                System.out.println("执行第" + finalI + "个线程.");
                return 1;
            });
            futureList.add(submit);
        }

        for (int i = 0; i < futureList.size(); i++) {
            Integer integer = null;
            try {
                integer = futureList.get(i).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            System.out.println("第" + i + "个线程结果获取完毕.");
            list.add(integer);
        }

        System.out.println("submit执行完毕!");
        System.out.println("submit结果" + list.size());
    }
}
