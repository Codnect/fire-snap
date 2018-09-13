package org.codnect.firesnap.mapping;


import org.codnect.firesnap.reflection.ReflectionUtil;
import org.codnect.firesnap.reflection.XProperty;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class PropertyContainer {

    private Class xClass;
    private Map<String, XProperty> persistencePropertyMap;

    public PropertyContainer(Class xClass) {
        this.xClass = xClass;
        persistencePropertyMap = new TreeMap<>();
    }

    /**
     *
     */
    public void processProperties() {

       // List<XProperty> fields = ReflectionUtil.getDeclaredFieldProperties(xClass);
       // List<XProperty> getters = ReflectionUtil.getDeclaredProperties(xClass);

    }

}
