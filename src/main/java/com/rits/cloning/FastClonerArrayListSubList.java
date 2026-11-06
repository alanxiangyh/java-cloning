package com.rits.cloning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerArrayListSubList implements FastCloner {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        List al = (List) t;
        int size = al.size();
        ArrayList l = new ArrayList(size);
        for (Object o : al) {
            l.add(cloner.deepClone(o, clones));
        }
        return l;
    }

}
