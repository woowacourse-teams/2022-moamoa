package com.woowacourse.acceptance.study;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.BDDMockito.given;

import com.woowacourse.acceptance.AcceptanceTest;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.study.service.DateTimeSystem;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import io.restassured.RestAssured;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.config.TriggerTask;

public class AutoCloseEnrollmentAcceptanceTest extends AcceptanceTest {

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
