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

    /**
     * In the managed resources, scan specified packages and
     * find annotated classes.
     *
     * @param managedResources managed resources
     */
    public static void scan(ManagedResources managedResources) {

        for(String packageName : managedResources.getAnnotatedPackages()) {
            try {
                List<Class> classes = PackageUtils.getClasses(packageName);
                for(Class classX : classes) {
                    if(classX.isAnnotationPresent(Model.class)) {
                        managedResources.addAnnotatedClass(classX);
                    }
                }
            } catch (Exception e) {
                Log.w(LOG_TAG, "the package not found (" + packageName + ")");
            }
        }

    }

}