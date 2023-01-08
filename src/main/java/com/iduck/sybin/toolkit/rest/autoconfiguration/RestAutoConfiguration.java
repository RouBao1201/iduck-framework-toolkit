package com.iduck.sybin.toolkit.rest.autoconfiguration;

import com.iduck.sybin.toolkit.rest.bean.RestAdapter;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * RestTemplate配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
@Configuration
public class RestAutoConfiguration {

    @Bean("restTemplate")
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        return new RestTemplate(getOkHttpClient());
    }

    @Bean("restAdapter")
    @ConditionalOnMissingBean(RestAdapter.class)
    public RestAdapter restAdapter(RestTemplate restTemplate) {
        return new RestAdapter(restTemplate);
    }

    private ClientHttpRequestFactory getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        return new OkHttp3ClientHttpRequestFactory(okHttpClient);
    }
}
