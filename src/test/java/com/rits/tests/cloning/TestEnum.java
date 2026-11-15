package com.rits.tests.cloning;

@SuppressWarnings("ALL")
public enum TestEnum {
    A("a") {
        public String someMethod() {
            return "Some String from A";
        }
    },


    B("b") {
        public String someMethod() {
            return "Some String from B";
        }
    },

    C("c") {
        public String someMethod() {
            return "Some String from C";
        }
    };

    public static Object o = new Object();
    private final String name;

    TestEnum(String name) {
        this.name = name;
    }

    public abstract String someMethod();
}