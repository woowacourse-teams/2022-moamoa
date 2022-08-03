package com.woowacourse.moamoa.study.schedule;

import com.woowacourse.moamoa.MoamoaApplication;
import com.woowacourse.moamoa.study.service.StudyService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AutoCloseEnrollmentTask extends TriggerTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoamoaApplication.class);

    public AutoCloseEnrollmentTask(final StudyService studyService) {
        super(runnable(studyService), new CronTrigger("@daily", ZoneId.of("Asia/Seoul")));
    }

    private static Runnable runnable(final StudyService studyService) {
        return new Runnable() {

            @Transactional
            public void run() {
                LOGGER.debug(LocalDateTime.now() + " : " + "start moamoa scheduled task!");
                studyService.autoUpdateStatus();
            }
        };
    }
}
