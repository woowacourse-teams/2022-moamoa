package com.woowacourse.moamoa.study.schedule;

import com.woowacourse.moamoa.study.service.StudyService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AutoCloseEnrollmentTask extends TriggerTask {

    public AutoCloseEnrollmentTask(final StudyService studyService) {
        super(runnable(studyService), new CronTrigger("@daily", ZoneId.of("Asia/Seoul")));
    }

    private static Runnable runnable(final StudyService studyService) {
        return () -> {
            log.debug("{} : start moamoa scheduled task!", LocalDateTime.now());
            studyService.autoUpdateStatus();
        };
    }
}
