package com.darkness.tasks.web.app;

import com.darkness.tasks.utils.logging.Loggable;
import com.darkness.tasks.utils.logging.LoggingLevel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    @Before("@annotation(loggable)")
    public void logMethodStart(@NotNull JoinPoint joinPoint, @NotNull Loggable loggable){
        log("Executing method: %s".formatted(joinPoint.getSignature().getName()),
                joinPoint.getTarget().getClass(),
                loggable.level());
    }

    @After("@annotation(loggable)")
    public void logMethodEnd(@NotNull JoinPoint joinPoint, @NotNull Loggable loggable){
        log("Method %s execution ended".formatted(joinPoint.getSignature().getName()),
                joinPoint.getTarget().getClass(),
                loggable.level());

    }

    private void log(@NotNull String message, @NotNull Class loggingClass, @NotNull LoggingLevel loggingLevel){
        Logger logger = LoggerFactory.getLogger(loggingClass);

        switch (loggingLevel){
            case INFO -> logger.info(message);
            case ERROR -> logger.error(message);
        }

    }
}
