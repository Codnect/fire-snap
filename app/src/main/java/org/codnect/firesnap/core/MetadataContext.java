package org.codnect.firesnap.core;

import org.codnect.xreflect.ReflectionManager;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class MetadataContext {

    private MetadataCollector metadataCollector;
    private ReflectionManager reflectionManager;

    public MetadataContext(MetadataCollector metadataCollector) {
        this.metadataCollector = metadataCollector;
        this.reflectionManager = new ReflectionManager();
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

    /**
     *
     * @return
     */
    public ReflectionManager getReflectionManager() {
        return reflectionManager;
    }

    /**
     *
     * @param reflectionManager
     */
    public void setReflectionManager(ReflectionManager reflectionManager) {
        this.reflectionManager = reflectionManager;
    }

}
