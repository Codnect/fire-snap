package org.codnect.firesnap.core;

/**
 * Sets and manages the configuration and metadata
 * for FireSnap.
 *
 * Created by Burak Koken on 12.5.2018.
 *
 * @author Burak Koken
 */
public class Configuration {

    private static final String LOG_TAG = Configuration.class.getSimpleName();

    private final MetadataSources metadataSources;

    public Configuration() {
        metadataSources = new MetadataSources();
    }

    /**
     * Add the class containing annotations to read its metadata.
     *
     * @param annotatedClass The class containing annotations
     * @return this (for method chaining)
     */
    public Configuration addAnnotatedClass(Class annotatedClass) {
        metadataSources.addAnnotatedClass(annotatedClass);
        return this;
    }

    /**
     * Add the package name to read the metadata of the classes
     * containing annotations in the given java package.
     *
     * @param packageName Java package name
     * @return this (for method chaining)
     */
    public Configuration addPackage(String packageName) {
        metadataSources.addPackage(packageName);
        return this;
    }

    /**
     * Set the configuration for FireSnap.
     */
    public void configure() {
        MetadataBuilder metadataBuilder = new MetadataBuilder(metadataSources);
        metadataBuilder.build();
    }

}
