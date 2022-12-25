package com.iduck.sybin.toolkit.rest.util;

import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate封装工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
public class RestAdaptor {

    private final RestTemplate restTemplate;

    public RestAdaptor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void call() {

    }
}
