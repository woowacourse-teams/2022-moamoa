package com.woowacourse.moamoa.study.batch;

import com.woowacourse.moamoa.study.service.StudyService;
import java.time.ZoneId;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AutoCloseEnrollmentTask extends TriggerTask {

    public AutoCloseEnrollmentTask(final StudyService studyService) {
        super(runnable(studyService), new CronTrigger("@daily", ZoneId.of("Asia/Seoul")));
    }

    private static Runnable runnable(final StudyService studyService) {
        return new Runnable() {

            @Transactional
            public void run() {
                studyService.autoCloseStudies();
            }
        };
    }
}
