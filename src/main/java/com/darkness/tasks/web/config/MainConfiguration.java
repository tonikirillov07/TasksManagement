package com.darkness.tasks.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:additional.properties")
public class MainConfiguration {
    @Value("${max_in_progress_tasks}")
    private int maxInProgressTasks;
    @Value("${default_page_size}")
    private int defaultPageSize;

    @Bean("maxInProgressTasks")
    public int maxInProgressTasks(){
        return maxInProgressTasks;
    }

    @Bean("defaultPageSize")
    public int defaultPageSize(){
        return defaultPageSize;
    }
}
