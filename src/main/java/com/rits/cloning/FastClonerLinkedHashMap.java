package com.rits.cloning;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerLinkedHashMap implements FastCloner<LinkedHashMap<?, ?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public LinkedHashMap<?, ?> clone(final LinkedHashMap<?, ?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final LinkedHashMap dest = new LinkedHashMap<>();
        for (final Map.Entry e : src.entrySet()) {
            dest.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return dest;
    }
}
