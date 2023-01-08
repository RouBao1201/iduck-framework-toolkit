package com.iduck.sybin.toolkit.common.util;

import com.iduck.sybin.toolkit.common.functions.ConsumerFunction;
import com.iduck.sybin.toolkit.common.functions.EitherHandleFunction;

import java.util.Collection;
import java.util.Map;

/**
 * 函数接口工具类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/1/8
 **/
public class Judge {
    /**
     * 若为true则执行逻辑
     *
     * @param flag 标志
     * @return 执行逻辑
     */
    public static ConsumerFunction isTrue(boolean flag) {
        return (runnable) -> {
            if (flag) {
                runnable.run();
            }
        };
    }

    /**
     * 若为false则执行逻辑
     *
     * @param flag 标志
     * @return 执行逻辑
     */
    public static ConsumerFunction isFalse(boolean flag) {
        return (runnable) -> {
            if (!flag) {
                runnable.run();
            }
        };
    }

    /**
     * 分支判断
     *
     * @param obj 判断数据
     * @return 分支执行函数
     */
    public static EitherHandleFunction either(Object obj) {
        return (trueHandle, falseHandle) -> {
            if (obj == null) {
                falseHandle.run();
                return;
            }
            if (obj instanceof Boolean) {
                if ((Boolean) obj) {
                    trueHandle.run();
                } else {
                    falseHandle.run();
                }
            } else if (obj instanceof String) {
                String str = (String) obj;
                if (str.length() == 0) {
                    falseHandle.run();
                } else {
                    trueHandle.run();
                }
            } else if (obj instanceof Collection) {
                if (((Collection<?>) obj).size() == 0) {
                    falseHandle.run();
                } else {
                    trueHandle.run();
                }
            } else if (obj instanceof Map) {
                if (((Map<?, ?>) obj).size() == 0) {
                    falseHandle.run();
                } else {
                    trueHandle.run();
                }
            } else {
                throw new RuntimeException("Judge ==> This type of data is not supported.");
            }
        };
    }

    private Judge() {

    }
}
