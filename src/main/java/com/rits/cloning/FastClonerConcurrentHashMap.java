package com.rits.cloning;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author kostantinos.kougios
 * <p>
 * 18 Oct 2011
 */
public class FastClonerConcurrentHashMap implements FastCloner {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        final ConcurrentHashMap<Object, Object> m = (ConcurrentHashMap) t;
        final ConcurrentHashMap result = new ConcurrentHashMap();
        for (final Map.Entry e : m.entrySet()) {
            result.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return result;
    }
}
