package org.codnect.firesnap.scan;

import org.codnect.firesnap.annotation.Embeddable;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.annotation.Model;

/**
 * Created by Burak Koken  on 20.9.2018.
 *
 * @author Burak Koken
 */
public class ClassDescriptor {

    private String name;
    private Class classX;
    private ClassCategory category;

    private ClassDescriptor(Class classX, String name, ClassCategory category) {
        this.name = name;
        this.classX = classX;
        this.category = category;
    }

    public static ClassDescriptor create(Class classX) {
        ClassCategory classCategory;
        if(classX.isAnnotationPresent(Model.class)
                || classX.isAnnotationPresent(MappedSuperClass.class)
                || classX.isAnnotationPresent(Embeddable.class)) {
            classCategory = ClassCategory.MODEL;
        } else {
            classCategory = ClassCategory.OTHER;
        }
        return new ClassDescriptor(classX, classX.getName(), classCategory);
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return classX;
    }

    public ClassCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClassDescriptor)) {
            return false;
        }
        ClassDescriptor other = (ClassDescriptor) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
