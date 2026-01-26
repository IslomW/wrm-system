package com.sharom.wrm.config;

import org.jspecify.annotations.Nullable;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

public class AsyncConfig implements AsyncConfigurer {


    @Override
    public @Nullable Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);  // базовое количество потоков
        executor.setMaxPoolSize(50);   // максимум
        executor.setQueueCapacity(500); // для очереди задач
        executor.setThreadNamePrefix("qr-executor-");
        executor.initialize();
        return executor;
    }
}
