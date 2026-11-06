package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author kostas.kougios
 * 07/01/19 - 20:08
 */
public class FastClonerConcurrentLinkedQueue implements FastCloner<ConcurrentLinkedQueue<?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ConcurrentLinkedQueue<?> clone(ConcurrentLinkedQueue<?> src, DeepCloner cloner, Map<Object, Object> clones) {
        ConcurrentLinkedQueue dest = new ConcurrentLinkedQueue();
        for (Object o : src) {
            dest.add(cloner.deepClone(o, clones));
        }
        return dest;
    }
}
