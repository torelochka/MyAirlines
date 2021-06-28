package ru.itis.zheleznov.impl.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(Logging)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("executing method = " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Object proceed = joinPoint.proceed();
        log.info("method return: " + proceed);
        return proceed;
    }
}
