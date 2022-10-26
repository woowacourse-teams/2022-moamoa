package com.woowacourse.moamoa.common.config.replication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DataSourceKey {

    SOURCE(KeyName.SOURCE_NAME, false),
    REPLICA_1(KeyName.REPLICA_1_NAME, true),
    REPLICA_2(KeyName.REPLICA_2_NAME, true);

    private final String key;
    private final boolean isReplica;

    DataSourceKey(final String key, final boolean isReplica) {
        this.key = key;
        this.isReplica = isReplica;
    }

    public static List<DataSourceKey> getReplicas() {
        return Arrays.stream(values())
                .filter(key -> key.isReplica)
                .collect(Collectors.toList());
    }

    public static class KeyName {
        public static final String SOURCE_NAME = "SOURCE";
        public static final String REPLICA_1_NAME = "REPLICA_1";
        public static final String REPLICA_2_NAME = "REPLICA_2";
    }
}
