package com.iduck.sybin.toolkit.page.utils;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iduck.sybin.toolkit.exception.utils.IExceptionHandler;
import com.iduck.sybin.toolkit.page.model.BasePage;
import com.iduck.sybin.toolkit.page.model.PageResult;
import com.iduck.sybin.toolkit.response.enums.RespEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;

/**
 * 分页查询工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
public final class IPageHelper {
    private static final Logger log = LoggerFactory.getLogger(IPageHelper.class);

    /**
     * 分页查询
     *
     * @param reqDto 请求体
     * @param select select接口
     * @param clazz  响应类型
     * @param <T1>   请求类型枚举
     * @param <T2>   响应类型枚举
     * @return PageResult分页通用响应体
     */
    public static <T extends BasePage, R> PageResult<R> doStart(T reqDto, ISelect select, Class<R> clazz) {
        if (reqDto.getPageNum() == null || reqDto.getPageSize() == null) {
            log.error("IPageHelper ==> The pageNum and pageSize cannot be null.");
            IExceptionHandler.pushServiceExc();
        }

        // 执行分页查询mapper
        PageInfo<R> pageInfo = PageHelper.startPage(reqDto.getPageNum(), reqDto.getPageSize())
                .doSelectPageInfo(select);

        // 组装分页通用响应体
        PageResult<R> respResult = new PageResult<>();
        setCommonData(reqDto, respResult);
        respResult.setTotal(pageInfo.getTotal());
        respResult.setList(pageInfo.getList());
        return respResult;
    }

    /**
     * 获取空分页对象
     *
     * @param reqDto 请求体
     * @param clazz  响应体类型
     * @param <T>    请求体枚举
     * @param <R>    响应体枚举
     * @return PageResult
     */
    public static <T extends BasePage, R> PageResult<R> doEmpty(T reqDto, Class<R> clazz) {
        PageResult<R> respResult = new PageResult<>();
        setCommonData(reqDto, respResult);
        respResult.setTotal(0);
        respResult.setList(new ArrayList<>(0));
        return respResult;
    }

    /**
     * 创建分页校验对象
     *
     * @return PageJudge
     */
    public static PageJudge buildJudge() {
        return new PageJudge(true);
    }

    private static <T extends BasePage, R> void setCommonData(T reqDto, PageResult<R> respResult) {
        respResult.setCode(RespEnum.SUCCESS.getCode());
        respResult.setMessage(RespEnum.SUCCESS.getMessage());
        respResult.setRespTime(new Date());
        respResult.setPageNum(reqDto.getPageNum());
        respResult.setPageSize(reqDto.getPageSize());
    }

    /**
     * 分页查询条件限制类
     */
    public static class PageJudge {
        private boolean conditionResult;

        public PageJudge(boolean conditionResult) {
            this.conditionResult = conditionResult;
        }

        /**
         * 限制条件（true才执行分页查询,false返回空分页对象）
         *
         * @param condition 判断条件
         * @return PageJudge
         */
        public PageJudge condition(boolean condition) {
            if (this.conditionResult) {
                this.conditionResult = condition;
            }
            return this;
        }

        /**
         * 执行分页查询（限制条件为true执行SQL查询,false返回空分页对象）
         *
         * @param reqDto 请求体
         * @param select 查询接口
         * @param clazz  响应体类型
         * @param <T>    请求体枚举
         * @param <R>    响应体枚举
         * @return PageResult
         */
        public <T extends BasePage, R> PageResult<R> doStart(T reqDto, ISelect select, Class<R> clazz) {
            if (this.conditionResult) {
                return IPageHelper.doStart(reqDto, select, clazz);
            } else {
                return IPageHelper.doEmpty(reqDto, clazz);
            }
        }
    }

    private IPageHelper() {

    }
}
