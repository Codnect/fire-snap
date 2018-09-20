package org.codnect.firesnap.scan;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Burak Koken  on 20.9.2018.
 *
 * @author Burak Koken
 */
public class ScanResultCollector {

    private Set<ClassDescriptor> classDescriptors;

    public ScanResultCollector() {
        classDescriptors = new HashSet<>();
    }

    public void addClassDescriptor(ClassDescriptor classDescriptor) {
        classDescriptors.add(classDescriptor);
    }

    public ScanResult toScanResult() {
        return new ScanResult(classDescriptors);
    }

}
