package com.system.artworkspace.artwork;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Aspect
@Component
public class RateLimitAspect {

    private Map<String, Map<Long, Long>> methodCallHistory = new HashMap<>();

    @Pointcut("@annotation(rateLimit)")
    public void rateLimitedMethods(RateLimit rateLimit) {}

    @Before("rateLimitedMethods(rateLimit)")
    public void beforeRateLimitedMethod(JoinPoint joinPoint, RateLimit rateLimit) throws RuntimeException {
        String methodName = joinPoint.getSignature().toShortString();
        long currentTime = System.currentTimeMillis();

        Map<Long, Long> callHistory = methodCallHistory.get(methodName);
        if (callHistory == null) {
            callHistory = new HashMap<>();
            methodCallHistory.put(methodName, callHistory);
        }

        long oneMinuteAgo = currentTime - 60000;

        Iterator<Map.Entry<Long, Long>> iterator = callHistory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Long> entry = iterator.next();
            if (entry.getValue() < oneMinuteAgo) {
                iterator.remove();
            }
        }

        if (callHistory.size() >= rateLimit.maxCalls()) {
            throw new RuntimeException("Rate limit exceeded for method: " + methodName);
        }

        callHistory.put(currentTime, currentTime);
    }
}