package org.codnect.firesnap.core;

import org.codnect.firesnap.scan.PackageMetadataScanner;

/**
 * Helps to create the metadata from the metadata sources.
 *
 * Created by Burak Koken on 12.5.2018.
 *
 * @author Burak Koken
 */
public class MetadataBuilder {

    private final MetadataSources metadataSources;

    public MetadataBuilder(MetadataSources metadataSources) {
        this.metadataSources = metadataSources;
    }

    /**
     * Create metadata from metadata sources
     */
    public void build() {
        /* create a new metadata metadata collector to hold the metadata */
        MetadataCollector metadataCollector = new MetadataCollector();
        /* create a new metadata context to hold everything */
        MetadataContext metadataContext = new MetadataContext(metadataCollector);
        /* get the managed resources */
        ManagedResources managedResources = prepareManagedResources();
        /* process the metadata sources */
        MetadataSourceProcessor processor = new AnnotationMetadataProcessor(metadataContext, managedResources);
        processor.prepare();
        processor.process();
    }

    /**
     * Prepare managed resources to create metadata.
     *
     * @return managed resources
     */
    private ManagedResources prepareManagedResources() {
        ManagedResources managedResources = new ManagedResources(metadataSources);
        PackageMetadataScanner.scan(managedResources);
        return managedResources;
    }

}
