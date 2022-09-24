package com.omega.cowalk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

//background task들을 위해서 Executor 세팅
@Configuration
@EnableAsync
@Component
public class TaskExecutorConfig {

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(11);
        taskExecutor.setQueueCapacity(100);

        return taskExecutor;
    }

    @Bean(name = "walkHistoryThreadPoolTaskExecutor")
    public TaskExecutor walkHistoryThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);    //기본 스레드 수
        taskExecutor.setMaxPoolSize(30);    //최대 스레드 수
        taskExecutor.setQueueCapacity(100); //Queue 사이즈

        return taskExecutor;
    }
}
