package com.sky.Aspect;

import com.sky.Annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.Annotation.AutoFill)")
    public void autoFillPoinCut(){}

    @Before("autoFillPoinCut()")
    //@Around + ProceedingJoinPoint：当你需要完全控制方法的执行过程（包括是否执行、何时执行、异常处理等）时使用
    //@Before + JoinPoint：当你只需要在方法执行前做一些事情，但不干预方法执行过程时使用
    public void autofill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获得方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //MethodSignature的专属方法，获取方法
        Method method = signature.getMethod();
        //获得
        AutoFill annotation = method.getAnnotation(AutoFill.class);
        Object[] args = joinPoint.getArgs();
        if(args==null||args.length==0){
            return;
        }
        Object entity=args[0];
//        AutoFill annotation1 = entity.getClass().getAnnotation(AutoFill.class);获取的是Employee类上的注解
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        if(annotation.value()==OperationType.INSERT){
            Method setUpdateTime = entity.getClass().getMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getMethod("setUpdateUser", Long.class);
            Method setCreateTime = entity.getClass().getMethod("setCreateTime", LocalDateTime.class);
            Method setCreateUser = entity.getClass().getMethod("setCreateUser", Long.class);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
            setCreateTime.invoke(entity,now);
            setCreateUser.invoke(entity,currentId);

        }else if(annotation.value()==OperationType.UPDATE){
            Method setUpdateTime = entity.getClass().getMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getMethod("setUpdateUser", Long.class);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }


    }
}

