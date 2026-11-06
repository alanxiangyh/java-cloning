package com.rits.cloning;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerTreeMap implements FastCloner {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        final TreeMap<Object, Object> m = (TreeMap) t;
        final TreeMap result = new TreeMap(m.comparator());
        for (final Map.Entry e : m.entrySet()) {
            result.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return result;
    }
}
