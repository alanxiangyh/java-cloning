package com.rits.cloning;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerHashMap implements FastCloner {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        final HashMap<Object, Object> m = (HashMap) t;
        final HashMap result = new HashMap();
        for (final Map.Entry e : m.entrySet()) {
            result.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return result;
    }
}
