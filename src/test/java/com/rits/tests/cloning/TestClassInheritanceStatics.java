package com.rits.tests.cloning;

import com.rits.cloning.Cloner;
import org.junit.jupiter.api.Test;

/**
 * @author kostas.kougios
 * Date: 12/06/17
 */
public class TestClassInheritanceStatics {

    @Test
    public void testStaticFields_Parent() {
        Cloner cloner = new Cloner();
        cloner.registerStaticFields(Parent.class);
        // Works fine
    }

    @Test
    public void testStaticFields_Child() {
        Cloner cloner = new Cloner();
        cloner.registerStaticFields(Child.class);
        // Fails
    }

    static class Parent {

        private static String staticFieldParent;
        private String instanceFieldParent;
    }

    static class Child extends Parent {

        private static String staticFieldChild;
        private String instanceFieldChild;
    }
}
