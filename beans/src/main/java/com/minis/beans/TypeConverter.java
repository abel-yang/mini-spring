package com.minis.beans;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/10 09:38
 */
public interface TypeConverter {

    /**
     * Convert the value to the required type (if necessary from a String).
     * <p>Conversions from String to any type will typically use the {@code setAsText}
     * method of the PropertyEditor class, or a Spring Converter in a ConversionService.
     * @param value the value to convert
     * @param requiredType the type we must convert to
     * (or {@code null} if not known, for example in case of a collection element)
     * @return the new value, possibly the result of type conversion
     * @throws TypeMismatchException if type conversion failed
     * @see java.beans.PropertyEditor#setAsText(String)
     * @see java.beans.PropertyEditor#getValue()
     * @see org.springframework.core.convert.ConversionService
     * @see org.springframework.core.convert.converter.Converter
     */
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;

}
