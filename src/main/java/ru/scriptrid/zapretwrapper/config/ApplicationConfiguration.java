package ru.scriptrid.zapretwrapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Ivan Selikhov
 */
@Configuration
public class ApplicationConfiguration {
    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool(Thread.ofPlatform().name("asyncExecutor").factory());
    }
}
