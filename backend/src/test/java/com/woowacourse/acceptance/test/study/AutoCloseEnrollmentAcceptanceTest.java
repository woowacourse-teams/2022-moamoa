package com.woowacourse.acceptance.test.study;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.acceptance.AcceptanceTest;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.config.TriggerTask;

class AutoCloseEnrollmentAcceptanceTest extends AcceptanceTest {

    @Autowired
    private ScheduledAnnotationBeanPostProcessor processor;

    @Autowired
    private TriggerTask sut;

    @DisplayName("자동 모집 종료 태스크가 TaskScheduler에 등록되어 있다.")
    @Test
    void taskWillExecuteDaily() {
        final Set<Task> registeredTasks = processor.getScheduledTasks()
                .stream()
                .map(ScheduledTask::getTask)
                .collect(Collectors.toSet());

        assertThat(registeredTasks).contains(sut);
    }
}
