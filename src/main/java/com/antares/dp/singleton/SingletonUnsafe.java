package com.antares.dp.singleton;

public class SingletonUnsafe {
    private static SingletonUnsafe instance;

    private SingletonUnsafe() {
    }

    public static SingletonUnsafe getInstance() {
        if (instance == null) {
            instance = new SingletonUnsafe();
        }
        return instance;
    }
}
