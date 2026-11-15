package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FastClonerListOf12 implements FastCloner<List<?>> {

    public List<?> clone(final List<?> src, final DeepCloner cloner, final Map<Object, Object> clones) {
        if (src.size() == 1) {
            return List.of(cloner.deepClone(src.get(0), clones));
        } else if (src.size() == 2) {
            Object o1 = cloner.deepClone(src.get(0), clones);
            Object o2 = cloner.deepClone(src.get(1), clones);
            return List.of(o1, o2);
        } else {
            return new ArrayList<>();
        }
    }

}
