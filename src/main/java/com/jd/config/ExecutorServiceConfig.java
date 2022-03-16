package com.jd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorServiceConfig {

    @Bean
    @Primary
    public ExecutorService executorService() {
        return new ThreadPoolExecutor(3, 100, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

}
