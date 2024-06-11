package com.winpams.core.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CatchAspect {

    @Pointcut("@annotation(com.winpams.core.annotations.Catch)")
    public void catchAnnotationMethod() {
    }

    @Pointcut("@within(com.winpams.core.annotations.Catch)")
    public void catchAnnotationClass() {
    }

    @Around("catchAnnotationMethod() || catchAnnotationClass()")
    public Object handleExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            // Handle the exception (log it, rethrow it, etc.)
            e.printStackTrace();
            return null;
        }
    }
}