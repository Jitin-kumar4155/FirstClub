package com.firstclub.core.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@AllArgsConstructor
public class AppConfig implements WebMvcConfigurer
{

    private ApiLogger apiLogger;

    @Override
    public void addInterceptors( InterceptorRegistry registry) {
        registry.addInterceptor(apiLogger);
    }
}
