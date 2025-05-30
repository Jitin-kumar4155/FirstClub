package com.firstclub.core.config;

import com.firstclub.core.constant.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;


@Slf4j
@Component
public class ApiLogger implements HandlerInterceptor
{

    @Value ( "${apiLogger.slowExecutionTime}" )
    private long slowExecutionTime;

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String correlationId = UUID.randomUUID().toString();
        MDC.put(Constants.CORRELATION_ID, correlationId);
        request.setAttribute(Constants.START_TIME, System.currentTimeMillis());
        request.setAttribute(Constants.CORRELATION_ID, correlationId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        long startTime = (Long) request.getAttribute(Constants.START_TIME);
        long endTime = System.currentTimeMillis();
        long executeTimeMillis = endTime - startTime;
        if (executeTimeMillis > slowExecutionTime) {
            log.warn("Slow API Response time detected for {} {}: {} ms, {} seconds ",
                request.getMethod(),
                request.getRequestURI(),
                executeTimeMillis,
                (double)executeTimeMillis / 1000);
        } else {
            log.info("Normal API Response time detected for {} {}: {} ms, {} seconds ",
                request.getMethod(),
                request.getRequestURI(),
                executeTimeMillis,
                (double)executeTimeMillis / 1000);
        }
        MDC.remove( Constants.CORRELATION_ID );
    }
}