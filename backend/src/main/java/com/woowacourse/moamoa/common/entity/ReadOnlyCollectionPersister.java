package com.woowacourse.moamoa.common.entity;

import org.hibernate.MappingException;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.CollectionDataAccess;
import org.hibernate.mapping.Collection;
import org.hibernate.persister.collection.BasicCollectionPersister;
import org.hibernate.persister.spi.PersisterCreationContext;

public class ReadOnlyCollectionPersister extends BasicCollectionPersister {

    public ReadOnlyCollectionPersister(final Collection collectionBinding,
                                       final CollectionDataAccess cacheAccessStrategy,
                                       final PersisterCreationContext creationContext)
            throws MappingException, CacheException {
        super(asInverse(collectionBinding), cacheAccessStrategy, creationContext);
    }

    private static Collection asInverse(Collection collection) {
        collection.setInverse(true);
        return collection;
    }
}
