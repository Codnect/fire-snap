package org.codnect.firesnap.scan;

import org.codnect.firesnap.core.ManagedResources;
import org.codnect.firesnap.core.MetadataSources;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class PackageMetadataScannerTest {

    @Test
    public void testScan() {
        MetadataSources metadataSources = new MetadataSources();
        metadataSources.addPackage("org.codnect.firesnap.model");

        ManagedResources managedResources = new ManagedResources(metadataSources);
        PackageMetadataScanner packageMetadataScanner = new PackageMetadataScanner();
        packageMetadataScanner.scan(managedResources);

        assertNotEquals(managedResources.getAnnotatedClasses(), 0);
    }

}
