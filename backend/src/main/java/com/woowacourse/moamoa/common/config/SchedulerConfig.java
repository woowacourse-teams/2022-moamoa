package com.woowacourse.moamoa.common.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    private final List<TriggerTask> tasks;

    public SchedulerConfig(final List<TriggerTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
        for (TriggerTask task : tasks) {
            taskRegistrar.addTriggerTask(task);
        }
    }
}
