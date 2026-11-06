package com.rits.cloning;

import java.util.Map;
import java.util.Set;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class FastClonerCustomMap<T extends Map> implements FastCloner<T> {

    public T clone(final T src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final T dest = getInstance(src);
        final Set<Map.Entry<Object, Object>> entrySet = src.entrySet();
        for (final Map.Entry e : entrySet) {
            dest.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return dest;
    }

    protected abstract T getInstance(T t);
}
