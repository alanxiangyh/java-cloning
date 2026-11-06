package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Fast Cloner for LinkedHashSet
 *
 * @author Tobias Weimer
 */
public class FastClonerLinkedHashSet implements FastCloner<LinkedHashSet<?>> {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public LinkedHashSet<?> clone(final LinkedHashSet<?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final LinkedHashSet dest = new LinkedHashSet<>();
        for (final Object o : src) {
            dest.add(cloner.deepClone(o, clones));
        }
        return dest;
    }
}
