package com.minis.beans.factory.config;

import java.util.*;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:55
 */
public class ConstructorArgumentValues {
    private final List<ConstructorArgumentValue> constructorArgumentValueList = new ArrayList<>();
    public ConstructorArgumentValues() {
    }
    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue) {
        this.constructorArgumentValueList.add(constructorArgumentValue);
    }
    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.constructorArgumentValueList.get(index);
    }
    public int getArgumentCount() {
        return (this.constructorArgumentValueList.size());
    }
    public boolean isEmpty() {
        return (this.constructorArgumentValueList.isEmpty());
    }
}
