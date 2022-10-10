package com.woowacourse.acceptance;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MockServiceServerResetter {

    @Pointcut("execution(* com.woowacourse.acceptance.SlackAlarmMockServer.sendAlarm(..))")
    private void slackMockServer() {}

    @Before("slackMockServer()")
    public void reset(JoinPoint joinPoint) {
        final Object target = joinPoint.getTarget();
        log.info(target.getClass().getName() + ":  mock service server reset");

        SlackAlarmMockServer slackAlarmMockServer = (SlackAlarmMockServer) target;
        slackAlarmMockServer.resetMockServer();
    }
}
