package com.freeefly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@SpringBootApplication
public class Config implements AsyncConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
    }

    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public ForkJoinPool forkJoinPool(){
        ForkJoinPool forkJoinPool = new ForkJoinPool(200);
        return forkJoinPool;
    }

    @Bean
    public ExecutorService taskExecutor(){
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        return executorService;
    }
}