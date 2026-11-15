package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.Map;
import java.util.TreeSet;

public class FastClonerTreeSet implements FastCloner<TreeSet<?>> {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public TreeSet<?> clone(TreeSet<?> src, DeepCloner cloner, Map<Object, Object> clones) {
        TreeSet dest = new TreeSet<>(src.comparator());
        for (Object o : src) {
            dest.add(cloner.deepClone(o, clones));
        }
        return dest;
    }
}
