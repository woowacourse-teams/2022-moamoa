package com.woowacourse.moamoa.study.schedule;

import com.woowacourse.moamoa.study.service.StudyService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class AutoCloseEnrollmentTask extends TriggerTask {

    public AutoCloseEnrollmentTask(final StudyService studyService) {
        super(runnable(studyService), new CronTrigger("@daily", ZoneId.of("Asia/Seoul")));
    }

    private static Runnable runnable(final StudyService studyService) {
        return new Runnable() {

            @Transactional
            public void run() {
                log.debug("{} : start moamoa scheduled task!", LocalDateTime.now());
                studyService.autoUpdateStatus();
            }
        };
    }
}
