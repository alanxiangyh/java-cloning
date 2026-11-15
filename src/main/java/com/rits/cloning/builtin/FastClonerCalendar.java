package com.rits.cloning.builtin;

import com.rits.cloning.DeepCloner;
import com.rits.cloning.FastCloner;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author kostantinos.kougios
 * <p>
 * 21 May 2009
 */
public class FastClonerCalendar implements FastCloner<Calendar> {

    public Calendar clone(final Calendar src, final DeepCloner cloner, final Map<Object, Object> clones) {
        GregorianCalendar dest = new GregorianCalendar();
        dest.setTimeInMillis(src.getTimeInMillis());
        dest.setTimeZone((TimeZone) src.getTimeZone().clone());
        return dest;
    }
}
