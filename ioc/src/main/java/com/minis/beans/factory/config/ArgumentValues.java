package com.minis.beans.factory.config;

import java.util.*;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:55
 */
public class ArgumentValues {
    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    private void addArgumentValue(Integer key, ArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }

    private void addGenericArgumentValue(ArgumentValue newValue) {
        if(newValue == null) {
            return;
        }
        this.genericArgumentValues.removeIf(currentValue -> newValue.getName().equals(currentValue.getName()));
        this.genericArgumentValues.add(newValue);
    }

    public ArgumentValue getGenericArgumentValue(String requiredName) {
        for (ArgumentValue valueHolder : this.genericArgumentValues) {
            if (valueHolder.getName() != null && (!valueHolder.getName().equals(requiredName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }
    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }
}
