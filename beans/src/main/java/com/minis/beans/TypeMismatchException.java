package com.minis.beans;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/10 09:40
 */
public class TypeMismatchException extends Exception{

    public TypeMismatchException() {
    }

    public TypeMismatchException(String message) {
        super(message);
    }
}
