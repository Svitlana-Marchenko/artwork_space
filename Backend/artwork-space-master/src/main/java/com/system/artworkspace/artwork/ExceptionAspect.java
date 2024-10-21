package com.system.artworkspace.artwork;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.config.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class ExceptionAspect {

    @Pointcut("execution(* com.system.artworkspace.artwork.ArtworkServiceImpl.*(..))")
    public void serviceMethods() {}

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void handleException(JoinPoint jp, Exception ex) {
        String methodName = jp.getSignature().getName();
        log.error("Error in " + methodName + " with text: " + ex.getMessage());
    }

}
