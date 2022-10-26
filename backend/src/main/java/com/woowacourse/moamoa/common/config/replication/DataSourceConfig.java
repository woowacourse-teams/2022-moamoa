package com.woowacourse.moamoa.common.config.replication;

import static com.woowacourse.moamoa.common.config.replication.DataSourceKey.KeyName.REPLICA_1_NAME;
import static com.woowacourse.moamoa.common.config.replication.DataSourceKey.KeyName.REPLICA_2_NAME;
import static com.woowacourse.moamoa.common.config.replication.DataSourceKey.KeyName.SOURCE_NAME;
import static com.woowacourse.moamoa.common.config.replication.DataSourceKey.REPLICA_1;
import static com.woowacourse.moamoa.common.config.replication.DataSourceKey.REPLICA_2;
import static com.woowacourse.moamoa.common.config.replication.DataSourceKey.SOURCE;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource determinedDataSource = routingDataSource(sourceDataSource(), replica1DataSource(),
                replica2DataSource());
        return new LazyConnectionDataSourceProxy(determinedDataSource);
    }

    @Bean
    @Qualifier(SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Qualifier(REPLICA_1_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.replica1")
    public DataSource replica1DataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Qualifier(REPLICA_2_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.replica2")
    public DataSource replica2DataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier(SOURCE_NAME) DataSource sourceDataSource,
            @Qualifier(REPLICA_1_NAME) DataSource replica1DataSource,
            @Qualifier(REPLICA_2_NAME) DataSource replica2DataSource
    ) {
        Map<Object, Object> dataSources = Map.of(
                SOURCE, sourceDataSource, REPLICA_1, replica1DataSource, REPLICA_2, replica2DataSource
        );

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(sourceDataSource);

        return routingDataSource;
    }
}
