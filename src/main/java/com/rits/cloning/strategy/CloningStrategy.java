package com.rits.cloning.strategy;

import java.lang.reflect.Field;

public interface CloningStrategy {

    Strategy strategyFor(Object toBeCloned, Field field);

    enum Strategy {
        @Deprecated(since = "2.0.1")
        NULL_INSTEAD_OF_CLONE, // return null instead of a clone
        DEFAULT_INSTEAD_OF_CLONE, // Clone as Java transient field behavior, set to default value (better primitive compatibility)
        SAME_INSTANCE_INSTEAD_OF_CLONE, // return same instance instead of a clone
        IGNORE // ignore this strategy for this instance
    }
}
