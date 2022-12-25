package com.iduck.sybin.toolkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/12/23
 **/
@SpringBootApplication
public class ToolkitApp {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ToolkitApp.class);
        app.run(args);
    }
}
