package org.codnect.firesnap.scan;

import java.util.Set;

/**
 * Created by Burak Koken  on 20.9.2018.
 *
 * @author Burak Koken
 */
public class ScanResult {

    private Set<ClassDescriptor> classDescriptors;

    public ScanResult(Set<ClassDescriptor> classDescriptors) {
        this.classDescriptors = classDescriptors;
    }

    public Set<ClassDescriptor> getClassDescriptors() {
        return classDescriptors;
    }

}
