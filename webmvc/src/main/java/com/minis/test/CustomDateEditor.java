package com.minis.test;

import com.minis.beans.PropertyEditor;
import com.minis.beans.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/10 09:09
 */
public class CustomDateEditor implements PropertyEditor {
    private Date value;
    private Class<Date> clz;
    private DateTimeFormatter formatter;
    private boolean allowEmpty;

    public CustomDateEditor() {
        this(Date.class);
    }

    public CustomDateEditor(Class<Date> dateClass) {
        this(dateClass, true);
    }

    public CustomDateEditor(Class<Date> dateClass, boolean allowEmpty) {
        this(dateClass, "yyyy-MM-dd", allowEmpty);
    }

    public CustomDateEditor(Class<Date> dateClass, String pattern, boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        this.clz = dateClass;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public void setAsText(String text) {
        if(this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
            return;
        }
        LocalDate localDate = LocalDate.parse(text, formatter);
        setValue(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @Override
    public void setValue(Object value) {
        this.value = (Date) value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object getAsText() {
        if(this.value == null) {
            return "";
        }
        LocalDate localDate = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(formatter);
    }
}
