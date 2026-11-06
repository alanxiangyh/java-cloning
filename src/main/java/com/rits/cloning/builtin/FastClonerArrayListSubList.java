package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerArrayListSubList implements FastCloner<List<?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<?> clone(final List<?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        int size = src.size();
        ArrayList dest = new ArrayList(size);
        for (Object o : src) {
            dest.add(cloner.deepClone(o, clones));
        }
        return dest;
    }
}
