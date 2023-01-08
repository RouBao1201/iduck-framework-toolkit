package com.iduck.sybin.toolkit.common.functions;

/**
 * 分支处理函数接口
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/1/8
 **/
@FunctionalInterface
public interface EitherHandleFunction {
    /**
     * 分支处理逻辑
     *
     * @param trueHandle  true分支处理逻辑
     * @param falseHandle false分支处理逻辑
     */
    void oneOf(Runnable trueHandle, Runnable falseHandle);
}
