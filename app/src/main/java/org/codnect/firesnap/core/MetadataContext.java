package org.codnect.firesnap.core;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class MetadataContext {

    private MetadataCollector metadataCollector;

    public MetadataContext(MetadataCollector metadataCollector) {
        this.metadataCollector = metadataCollector;
    }

    /**
     * Get the metadata collector.
     *
     * @return metadata collector
     */
    public MetadataCollector getMetadataCollector() {
        return metadataCollector;
    }

    /**
     * Set the metadata collector.
     *
     * @param metadataCollector a new metadata collector
     */
    public void setMetadataCollector(MetadataCollector metadataCollector) {
        this.metadataCollector = metadataCollector;
    }

}
