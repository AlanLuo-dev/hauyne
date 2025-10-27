package com.luoyx.hauyne.admin.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志打印切面
 *
 * @author 罗英雄
 */
@Slf4j
@Component
@Aspect
@Order(100)
@RequiredArgsConstructor
public class RequestLogAspect {

    private static final String LINE = "————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————";

    private final ObjectMapper objectMapper;

    @Pointcut("execution(* com.luoyx.hauyne.admin.*.controller..*(..))")
    public void requestServer() {
    }


    /**
     * 环绕通知
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("requestServer()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("日志开始");
        long start = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object result = proceedingJoinPoint.proceed();

        log.info("\n[IP]\t\t==> {}\t[HTTP方法] ==> {}\t[URL] ==> {}\n[类方法]\t\t==> {}\n[请求参数]\t==> {}\n[返回结果]\t==> {}\n[耗时]\t\t==> {}ms\n{}",
                request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURL().toString(),
                String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                        proceedingJoinPoint.getSignature().getName()),
                objectMapper.writeValueAsString(getRequestParamsByProceedingJoinPoint(proceedingJoinPoint)),
                result,
                (System.currentTimeMillis() - start),
                LINE);

        return result;
    }


    @AfterThrowing(pointcut = "requestServer()", throwing = "e")
    public void doAfterThrow(JoinPoint joinPoint, RuntimeException e) throws JsonProcessingException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("\n异常\n[IP]\t\t==> {}\n[URL]\t\t==> {}\n[HTTP方法]\t==> {}\n[类方法]\t\t==> {}\n[请求参数]\t==> {}\n[异常信息]\t==> {}\n{}",

                request.getRemoteAddr(),
                request.getRequestURL().toString(),
                request.getMethod(),
                String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName()),
                objectMapper.writeValueAsString(getRequestParamsByJoinPoint(joinPoint)),
                e.getMessage(),
                LINE);
    }

    /**
     * 获取入参
     *
     * @param proceedingJoinPoint
     * @return
     */
    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> getRequestParamsByJoinPoint(JoinPoint joinPoint) {
        // 参数名
        String[] paramNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] paramValues = joinPoint.getArgs();

        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];

            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;

                //获取文件名
                value = file.getOriginalFilename();
            } else if (value instanceof BeanPropertyBindingResult) {
                continue;
            }

            requestParams.put(paramNames[i], value);
        }

        return requestParams;
    }
}
