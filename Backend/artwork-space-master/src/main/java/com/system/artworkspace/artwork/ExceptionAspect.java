package com.system.artworkspace.artwork;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(* com.system.artworkspace.artwork.ArtworkController.*(..))")
    public void serviceMethods() {}

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void handleException(JoinPoint jp, Exception ex) {
        String methodName = jp.getSignature().getName();
        logger.error("Error in " + methodName + " with text: " + ex.getMessage());
    }
}
