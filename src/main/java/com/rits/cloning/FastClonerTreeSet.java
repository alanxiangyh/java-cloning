package com.rits.cloning;

import java.util.Map;
import java.util.TreeSet;

public class FastClonerTreeSet implements FastCloner {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object clone(Object t, DeepCloner cloner, Map<Object, Object> clones) {
        TreeSet treeSet = (TreeSet) t;
        TreeSet result = new TreeSet(treeSet.comparator());
        for (Object o : treeSet) {
            result.add(cloner.deepClone(o, clones));
        }
        return result;
    }
}
