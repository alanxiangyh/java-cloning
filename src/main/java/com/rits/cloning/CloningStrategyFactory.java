package com.rits.cloning;

import java.lang.annotation.Annotation;

public class CloningStrategyFactory {

    public static CloningStrategy annotatedField(final Class<? extends Annotation> annotationClass, final CloningStrategy.Strategy strategy) {
        return (toBeCloned, field) -> {
            if (toBeCloned == null) return CloningStrategy.Strategy.IGNORE;
            if (field.getDeclaredAnnotation(annotationClass) != null) return strategy;
            return CloningStrategy.Strategy.IGNORE;
        };
    }
}