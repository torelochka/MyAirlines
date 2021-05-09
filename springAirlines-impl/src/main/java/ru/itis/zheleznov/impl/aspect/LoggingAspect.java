package ru.itis.zheleznov.impl.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation(Logging)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("executing method = " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Object proceed = joinPoint.proceed();
        System.out.println("method return: " + proceed);
        return proceed;
    }
}
