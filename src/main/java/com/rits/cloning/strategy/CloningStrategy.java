package com.rits.cloning.strategy;

import java.lang.reflect.Field;

public interface CloningStrategy {

    Strategy strategyFor(Object toBeCloned, Field field);

    enum Strategy {
        NULL_INSTEAD_OF_CLONE, // return null instead of a clone
        SAME_INSTANCE_INSTEAD_OF_CLONE, // return same instance instead of a clone
        IGNORE // ignore this strategy for this instance
    }
}
