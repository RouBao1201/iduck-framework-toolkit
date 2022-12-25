package com.iduck.sybin.toolkit.page.util;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iduck.sybin.toolkit.exception.util.IExceptionHandler;
import com.iduck.sybin.toolkit.page.model.BasePage;
import com.iduck.sybin.toolkit.page.model.PageResult;
import com.iduck.sybin.toolkit.response.enums.RespEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 分页查询工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/22
 **/
public class IPageHelper {
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
    public static <T1 extends BasePage, T2> PageResult<T2> doStart(T1 reqDto, ISelect select, Class<T2> clazz) {
        if (reqDto.getPageNum() == null || reqDto.getPageSize() == null) {
            log.error("IPageHelper ==> The pageNum and pageSize cannot be null.");
            IExceptionHandler.pushServiceExc();
        }

        // 执行分页查询mapper
        PageInfo<T2> pageInfo = PageHelper.startPage(reqDto.getPageNum(), reqDto.getPageSize())
                .doSelectPageInfo(select);

        // 组装分页通用响应体
        PageResult<T2> respResult = new PageResult<>();
        respResult.setCode(RespEnum.SUCCESS.getCode());
        respResult.setMessage(RespEnum.SUCCESS.getMessage());
        respResult.setRespTime(new Date());
        respResult.setPageNum(reqDto.getPageNum());
        respResult.setPageSize(reqDto.getPageSize());
        respResult.setTotal(pageInfo.getTotal());
        respResult.setList(pageInfo.getList());
        return respResult;
    }

    private IPageHelper() {

    }
}
