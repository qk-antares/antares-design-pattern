package com.antares.dp.construct.singleton;

public class SingletonStaticInnerClass {
    private static class InstanceHolder {
        private static final SingletonStaticInnerClass INSTANCE = new SingletonStaticInnerClass();
    }
    
    private SingletonStaticInnerClass() {
    }
    
    public static SingletonStaticInnerClass getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
