package com.rits.cloning;

import java.util.HashSet;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerHashSet implements FastCloner<HashSet<?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public HashSet<?> clone(final HashSet<?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final HashSet dest = new HashSet<>();
        for (final Object o : src) {
            dest.add(cloner.deepClone(o, clones));
        }
        return dest;
    }
}
