package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerLinkedList implements FastCloner<LinkedList<?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public LinkedList<?> clone(final LinkedList<?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final LinkedList dest = new LinkedList<>();
        for (final Object o : src) {
            dest.add(cloner.deepClone(o, clones));
        }
        return dest;
    }
}
