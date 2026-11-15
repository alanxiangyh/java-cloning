package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerHashMap implements FastCloner<HashMap<?, ?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public HashMap<?, ?> clone(final HashMap<?, ?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        final HashMap<Object, Object> m = (HashMap) src;
        final HashMap dest = new HashMap<>();
        for (final Map.Entry e : m.entrySet()) {
            dest.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
        }
        return dest;
    }
}
