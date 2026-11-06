package com.rits.cloning;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerLinkedHashMap implements FastCloner {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        final LinkedHashMap<?, ?> al = (LinkedHashMap) t;
        final LinkedHashMap result = new LinkedHashMap();
        for (final Map.Entry e : al.entrySet()) {
            result.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return result;
    }
}
