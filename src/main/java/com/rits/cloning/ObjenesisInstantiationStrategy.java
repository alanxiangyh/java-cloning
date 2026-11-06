package com.rits.cloning;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

/**
 * @author kostantinos.kougios
 * <p>
 * 17 Jul 2012
 */
public class ObjenesisInstantiationStrategy implements InstantiationStrategy {

    private static final ObjenesisInstantiationStrategy instance = new ObjenesisInstantiationStrategy();
    private final Objenesis objenesis = new ObjenesisStd();

    public static ObjenesisInstantiationStrategy getInstance() {
        return instance;
    }

    public <T> T newInstance(Class<T> c) {
        return objenesis.newInstance(c);
    }

    public <T> ObjectInstantiator<T> getInstantiatorOf(Class<T> c) {
        return objenesis.getInstantiatorOf(c);
    }
}
