package com.learn.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.internal.EnableZipkinServer;

/**
 * @author bill
 * @version 1.0
 * @date 2020/9/29 16:27
 */
@EnableZipkinServer
@SpringBootApplication
public class SleuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(SleuthApplication.class);
    }
}
