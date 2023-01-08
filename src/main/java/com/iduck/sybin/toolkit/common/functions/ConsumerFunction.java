package com.iduck.sybin.toolkit.common.functions;

/**
 * 消费执行接口
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/1/8
 **/
@FunctionalInterface
public interface ConsumerFunction {
    /**
     * 执行消费逻辑
     *
     * @param runnable runnable
     */
    void execute(Runnable runnable);
}
