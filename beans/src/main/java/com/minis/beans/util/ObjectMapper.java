package com.minis.beans.util;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/11 09:07
 */
public interface ObjectMapper {

    void setDateFormat(String dateFormat);
    void setDecimalFormat(String decimalFormat);
    String writeValuesAsString(Object obj);
}
