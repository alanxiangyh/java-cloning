package com.rits.cloning;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kostantinos.kougios
 * <p>
 * 18 Oct 2011
 */
public class FastClonerConcurrentHashMap implements FastCloner<ConcurrentHashMap<?, ?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ConcurrentHashMap<?, ?> clone(final ConcurrentHashMap<?, ?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final ConcurrentHashMap dest = new ConcurrentHashMap<>();
        for (final Map.Entry<?, ?> e : src.entrySet()) {
            dest.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return dest;
    }
}
