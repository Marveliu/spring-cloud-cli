package com.qianlq.customer.interceptor.log;

import com.qianlq.customer.util.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author CoderQian
 * @date 2018-09-28 下午2:29
 * mail: qianlq0824@gmail.com
 * <p>
 * 日志切面
 */

@Component
@Aspect
public class ServiceLogAOP {

    @Pointcut("execution(public * com.qianlq.customer.service.impl.*.*(..))")
    public void logTarget() {
    }

    @Around("logTarget()")
    public Object serviceThreadLocalHandle(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();
        Parameter[] parameters = method.getParameters();
        String targetClassName = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
        LogUtils.info("className: " + targetClassName);
        LogUtils.info("methodName: " + methodName);
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                LogUtils.info(parameters[i].getName() + ": " + parameters[i].getType().getName() + ": " + args[i]);
            }
        }
        return joinPoint.proceed();
    }
}
