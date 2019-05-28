package com.freeefly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
public class Config {
    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
//        ConfigurableApplicationContext context = SpringApplication.run(Config.class, args);
//        NettyWebServer nettyWebServer = context.getBean(NettyWebServer.class);
//        nettyWebServer.start();
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