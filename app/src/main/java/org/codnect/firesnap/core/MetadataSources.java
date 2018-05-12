package org.codnect.firesnap.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Keeps the specified metadata sources for configuration.
 *
 * Created by Burak Koken on 12.5.2018.
 *
 * @author Burak Koken
 */
public class MetadataSources {

    private Set<Class> annotatedClasses;
    private Set<String> annotatedPackages;

    public MetadataSources() {
        annotatedClasses = new HashSet<>();
        annotatedPackages = new HashSet<>();
    }

    /**
     * Get the annotated classes set.
     *
     * @return annotated classes set
     */
    public Set<Class> getAnnotatedClasses() {
        return annotatedClasses;
    }

    /**
     * Get the annotated packages set.
     *
     * @return annotated packages set
     */
    public Set<String> getAnnotatedPackages() {
        return annotatedPackages;
    }

    /**
     * Add the class containing annotations to read its metadata.
     *
     * @param annotatedClass The class containing annotations
     * @return this (for method chaining)
     */
    public MetadataSources addAnnotatedClass(Class annotatedClass) {
        annotatedClasses.add(annotatedClass);
        return this;
    }

    /**
     * Add the package name to read the metadata of the classes
     * containing annotations in the java package.
     *
     * @param packageName Java package name
     * @return this (for method chaining)
     */
    public MetadataSources addPackage(String packageName) {

        if(packageName == null) {
            throw new IllegalArgumentException("The specified package name cannot be null");
        }

        annotatedPackages.add(packageName);
        return this;
    }

}
