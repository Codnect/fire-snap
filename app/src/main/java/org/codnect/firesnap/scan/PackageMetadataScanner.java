package org.codnect.firesnap.scan;

import android.util.Log;

import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.ManagedResources;

import java.util.List;

/**
 * Created by Burak Koken on 25.2.2018.
 *
 * @author Burak Koken
 */
public class PackageMetadataScanner {

    private static final String LOG_TAG = PackageMetadataScanner.class.getSimpleName();

    public PackageMetadataScanner() {

    }

    /**
     * In the managed resources, scan specified packages and
     * find annotated classes.
     *
     * @param managedResources managed resources
     */
    public void scan(ManagedResources managedResources) {
        ScanResultCollector scanResultCollector = new ScanResultCollector();
        for(String packageName : managedResources.getAnnotatedPackages()) {
            try {
                List<Class> classes = PackageUtils.getClasses(packageName);
                for(Class classX : classes) {
                    scanResultCollector.addClassDescriptor(ClassDescriptor.create(classX));
                }
            } catch (Exception e) {
                Log.w(LOG_TAG, "the package not found (" + packageName + ")");
            }
        }
        applyResults(scanResultCollector, managedResources);
    }

    /**
     *
     * @param scanResultCollector
     * @param managedResources
     */
    private void applyResults(ScanResultCollector scanResultCollector, ManagedResources managedResources) {
        ScanResult scanResult = scanResultCollector.toScanResult();
        for(ClassDescriptor classDescriptor : scanResult.getClassDescriptors()) {
            if(classDescriptor.getCategory() == ClassCategory.MODEL) {
                managedResources.addAnnotatedClass(classDescriptor.getType());
            }
        }
    }

}