package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerTreeMap implements FastCloner<TreeMap<?, ?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public TreeMap<?, ?> clone(final TreeMap<?, ?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final TreeMap dest = new TreeMap<>(src.comparator());
        for (final Map.Entry<?, ?> e : src.entrySet()) {
            dest.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return dest;
    }
}
