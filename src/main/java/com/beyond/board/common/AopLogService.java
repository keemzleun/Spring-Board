package com.beyond.board.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

// @Aspect : aop 코드임을 명시
@Aspect
@Component
@Slf4j
public class AopLogService {
    // aop의 대상(공통화의 대상)이 되는 controller, service 등의 위치를 명시
    @Pointcut("within(@org.springframework.stereotype.Controller *)")   // 모든 컨트롤러 어노테이션 대상
    public void controllerPointCut(){

    }

//    // 방법 1. around를 통해 controller와 걸쳐져 있는 사용 패턴
//    @Around("controllerPointCut()")
//    //jointPoint는 사용자가 실행하려고 하는 코드를 의미하고, 위에서 정의한 Pointcut을 의미
//    public Object controllerLogger(ProceedingJoinPoint joinPoint){
//        log.info("aop start");
//        log.info("Method명 :" + joinPoint.getSignature().getName());
//
//        // 직업 HttServletRequest객체를 꺼내는 법
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        log.info("HTTP 메서드" + request.getMethod());
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectNode objectNode = objectMapper.valueToTree(parameterMap);
//        log.info("user input: " + objectNode);
//
//        Object result = null;
//        try {
//            // 사용자가 실행하고자 하는 코드 실행부
//            result = joinPoint.proceed();
//        }catch (Throwable e){
//            throw new RuntimeException(e);
//        }
//
//        log.info("aop end");
//        return result;
//    }

    // 사용 방법 2. Before, After 어노테이션 사용
    @Before("controllerPointCut()")
    public void beforeController(JoinPoint joinPoint){
        log.info("aop start");
        log.info("Method명 :" + joinPoint.getSignature().getName());

        // 직업 HttServletRequest객체를 꺼내는 법
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("HTTP 메서드" + request.getMethod());
    }

    @After("controllerPointCut()")
    public void afterController(){
        log.info("aop end");
    }
}
