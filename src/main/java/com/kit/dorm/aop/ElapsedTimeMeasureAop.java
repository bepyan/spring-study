package com.kit.dorm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class ElapsedTimeMeasureAop {
    //    @Around("execution(* com.kit.dorm..*(..))")
    @Around("@annotation(com.kit.dorm.annotation.ElapsedTimeLog)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {
            stopWatch.stop();
            System.out.println(joinPoint.toString() + " stopWatch.getTotalTimeMillis() = " + stopWatch.getTotalTimeMillis());
        }
    }
}

