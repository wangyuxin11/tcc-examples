package org.tcc.examples.core.utils;

import java.util.Collection;

public final class CollectionUtils {

    private CollectionUtils() {

    }

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }
}