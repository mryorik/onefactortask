package com.onefactor.api;

import com.onefactor.utils.HttpRequestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Yaroslav Frolikov
 * Date: 14.02.16 1:17
 */
@Aspect
@Component
public class ApiLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(ApiLoggingAspect.class);

    @Around("execution(* com.onefactor.controller.*.*(..))")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        result = pjp.proceed(pjp.getArgs());
        stopWatch.stop();

        HttpServletRequest request = HttpRequestUtils.getRequest();

        String response = "";
        if (result != null && result instanceof ApiResponse) {
            ApiResponse apiResponse = (ApiResponse) result;
            response = String.format("%d \"%s\"",
                    apiResponse.getStatus().getCode(),
                    apiResponse.getStatus().getMessage());
        }

        log.info("{} {} {}.{} {} {}ms", new Object[]{
                request.getMethod(),
                request.getRequestURI(),
                pjp.getTarget().getClass().getSimpleName(),
                pjp.getSignature().getName(),
                response,
                stopWatch.getTotalTimeMillis()
        });

        return result;
    }
}
