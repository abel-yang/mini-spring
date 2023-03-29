package com.minis.beans.factory.config;

import java.util.*;

/**
 * @author abel
 * @version 1.0
 * @date 2023/3/29 10:55
 */
public class ArgumentValues {
    private final List<ArgumentValue> argumentValueList = new ArrayList<>();
    public ArgumentValues() {
    }
    public void addArgumentValue(ArgumentValue argumentValue) {
        this.argumentValueList.add(argumentValue);
    }
    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.argumentValueList.get(index);
    }
    public int getArgumentCount() {
        return (this.argumentValueList.size());
    }
    public boolean isEmpty() {
        return (this.argumentValueList.isEmpty());
    }
}
