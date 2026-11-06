package com.rits.cloning;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerArrayList implements FastCloner {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        ArrayList al = (ArrayList) t;
        int size = al.size();
        ArrayList l = new ArrayList(size);
        for (Object o : al) {
            l.add(cloner.deepClone(o, clones));
        }
        return l;
    }

}
