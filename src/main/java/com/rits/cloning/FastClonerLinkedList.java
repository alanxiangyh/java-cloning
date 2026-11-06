package com.rits.cloning;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerLinkedList implements FastCloner {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        final LinkedList al = (LinkedList) t;
        final LinkedList l = new LinkedList();
        for (final Object o : al) {
            l.add(cloner.deepClone(o, clones));
        }
        return l;
    }
}
