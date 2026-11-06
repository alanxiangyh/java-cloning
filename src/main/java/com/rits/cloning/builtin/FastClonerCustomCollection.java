package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.Collection;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class FastClonerCustomCollection<T extends Collection> implements FastCloner<T> {

    public abstract T getInstance(T o);

    public T clone(final T src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final T dest = getInstance(src);
        for (final Object o : src) {
            dest.add(cloner.deepClone(o, clones));
        }
        return dest;
    }
}
