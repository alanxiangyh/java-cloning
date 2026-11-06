package com.rits.cloning;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerCalendar implements FastCloner {

    public Object clone(final Object t, final DeepCloner cloner, final Map<Object, Object> clones) {
        GregorianCalendar gc = new GregorianCalendar();
        Calendar c = (Calendar) t;
        gc.setTimeInMillis(c.getTimeInMillis());
        gc.setTimeZone((TimeZone) c.getTimeZone().clone());
        return gc;
    }
}
