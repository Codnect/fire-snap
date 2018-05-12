package org.codnect.firesnap.scan;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Burak Koken on 25.2.2018.
 *
 * @author Burak Koken
 */
public class PackageUtils {

    /**
     * Get all classes for specified package
     *
     * @param packageName
     * @return all classes for specified package
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class> getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(packagePath);

        List<File> directories = new ArrayList<>();
        URL resource;
        while (resources.hasMoreElements()) {
            resource = resources.nextElement();
            directories.add(new File(resource.getFile()));
        }

        List<Class> classes = new ArrayList<>();
        for(File directory : directories) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    /**
     * Find all classes for specified package
     *
     * @param directory sub directory for package
     * @param packageName package name
     * @return all classes for specified package
     * @throws ClassNotFoundException
     */
    public static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();

        if(!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        String className;
        for(File file : files) {

            if(file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if(file.getName().endsWith(".class")) {
                className = file.getName().substring(0, file.getName().length() - ".class".length());
                classes.add(Class.forName(packageName + "." + className));
            }

        }

        return classes;
    }

}