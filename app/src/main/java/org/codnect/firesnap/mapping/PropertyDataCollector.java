package org.codnect.firesnap.mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak Koken on 23.9.2018.
 *
 * @author Burak Koken
 */
public class PropertyDataCollector {

    private List<PropertyData> propertyDataList;

    public PropertyDataCollector() {
        propertyDataList = new ArrayList<>();
    }

    /**
     *
     * @param propertyData
     */
    public void addPropertyData(PropertyData propertyData) {
        propertyDataList.add(propertyData);
    }

    /**
     *
     * @return
     */
    public List<PropertyData> getPropertyDataList() {
        return propertyDataList;
    }

    /**
     *
     * @return
     */
    public int getPropertyCount() {
        return propertyDataList.size();
    }

}
