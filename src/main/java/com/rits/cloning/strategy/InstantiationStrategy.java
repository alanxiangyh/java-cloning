package com.rits.cloning.strategy;

import org.objenesis.instantiator.ObjectInstantiator;

/**
 * @author kostantinos.kougios
 * <p>
 * 17 Jul 2012
 */
public interface InstantiationStrategy {

    <T> T newInstance(final Class<T> c);

    <T> ObjectInstantiator<T> getInstantiatorOf(Class<T> c);
}
