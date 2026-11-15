package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.EnumMap;
import java.util.Map;

/**
 * Fast Cloner for EnumMaps
 *
 * @author Tobias Weimer
 */
public class FastClonerEnumMap implements FastCloner<EnumMap<?, ?>> {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public EnumMap<?, ?> clone(final EnumMap<?, ?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        // make a shallow copy of the original EnumMap
        // since there is no no-arg-constructor
        final EnumMap dest = new EnumMap(src);

        // Now clone the values
        for (final Map.Entry<? extends Enum<?>, ?> e : src.entrySet()) {
            // No need to clone the key, since it is an Enum
            // However, the value MUST be cloned
            dest.put(e.getKey(), cloner.deepClone(e.getValue(), clones));
        }
        return dest;
    }
}
