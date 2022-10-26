package com.woowacourse.moamoa.common.config.replication;

import static com.woowacourse.moamoa.common.config.replication.DataSourceKey.*;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    private final RandomReplicaKeys randomReplicaKeys = new RandomReplicaKeys();

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if (isReadOnly) {
            return randomReplicaKeys.next();
        }

        return SOURCE;
    }
}
