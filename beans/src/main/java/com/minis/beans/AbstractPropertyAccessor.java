package com.minis.beans;

import com.minis.beans.factory.config.PropertyValue;
import com.minis.beans.factory.config.PropertyValues;

/**
 * @author abel
 * @version 1.0
 * @date 2023/4/12 09:03
 */
public abstract class AbstractPropertyAccessor extends PropertyEditorRegistrySupport{
    PropertyValues pvs;



    /**
     * 绑定参数值
     *
     * @param pvs
     */
    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    protected abstract void setPropertyValue(PropertyValue pv);
}
