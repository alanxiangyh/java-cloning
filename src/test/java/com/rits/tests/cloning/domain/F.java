package com.rits.tests.cloning.domain;

import com.rits.cloning.Freezable;

/**
 * @author kostantinos.kougios
 * <p>
 * 15 Nov 2010
 */
public class F implements Freezable {

    private boolean frozen = false;

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }
}
