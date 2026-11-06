package com.rits.cloning;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FastClonerSetOf12 implements FastCloner<Set<?>> {

    public Set<?> clone(final Set<?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        Object[] a = src.toArray();
        if (src.size() == 1) {
            return Set.of(cloner.deepClone(a[0], clones));
        } else if (src.size() == 2) {
            Object o1 = cloner.deepClone(a[0], clones);
            Object o2 = cloner.deepClone(a[1], clones);
            return Set.of(o1, o2);
        } else {
            return new HashSet<>();
        }
    }
}
