package com.beyond.board.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// @Aspect : aop 코드임을 명시
@Aspect
@Component
@Slf4j
public class AopLogService {
    // aop의 대상(공통화의 대상)이 되는 controller, service 등의 위치를 명시
    @Pointcut("within(@org.springframework.stereotype.Controller *)")   // 모든 컨트롤러 어노테이션 대상
    public void controllerPointCut(){

    }

}
