package org.codnect.firesnap.inheritance;

import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.mapping.Node;
import org.codnect.firesnap.mapping.SimpleValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public abstract class PersistentClass {

    private String aliasName;
    private String modelName;
    private String className;
    private boolean isAbstract;
    private List<Subclass> subclasses;
    private String discriminatorValue;
    private MetadataContext metadataContext;

    public PersistentClass(MetadataContext metadataContext) {
        this.metadataContext = metadataContext;
        this.subclasses = new ArrayList<>();
    }

    /**
     * Get the alias name of the persistence class.
     *
     * @return the alias name of the persistence class
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * Set the alias name of the persistence class.
     *
     * @param aliasName new alias name
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    /**
     * Get the model name of the persistence class.
     *
     * @return the model name of the persistence class
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Set the model name of the persistence class.
     *
     * @param modelName new model name
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Get the model name of the persistence class.
     *
     * @return the class name of the persistence class
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set the class name of the persistence class.
     *
     * @param className new class name
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     *
     * @return
     */
    public boolean isAbstract() {
        return isAbstract;
    }

    /**
     *
     * @param isAbstract
     */
    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    /**
     *
     * @return
     */
    public abstract boolean isNodeOwner();

    /**
     *
     * @param node
     */
    public abstract void setNode(Node node);

    /**
     *
     * @return
     */
    public abstract Node getNode();

    /**
     *
     * @return
     */
    public abstract PersistentClass getSuperModelClass();

    /**
     *
     * @param subclass
     */
    public void addSubclass(Subclass subclass) {
        PersistentClass superClass = getSuperModelClass();
        while (superClass != null) {
            if(superClass.getModelName().equals(subclass.getModelName())) {
                throw new MappingException("Circular Mapping : " + subclass.getModelName());
            }
            superClass = superClass.getSuperModelClass();
        }
        subclasses.add(subclass);
    }

    /**
     *
     * @return
     */
    public boolean hasSubclasses() {
        if(subclasses.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public String getDiscriminatorValue() {
        return discriminatorValue;
    }

    /**
     *
     * @param discriminatorValue
     */
    public void setDiscriminatorValue(String discriminatorValue) {
        this.discriminatorValue = discriminatorValue;
    }

    /**
     *
     * @return
     */
    public abstract Node getRootNode();

    /**
     *
     * @return
     */
    public abstract RootClass getRootClass();

    /**
     *
     * @return
     */
    public abstract Class getPersistentClass();

    /**
     *
     */
    public abstract void setPersistentClass(Class persistentClass);

    /**
     *
     */
    public abstract SimpleValue getDiscriminator();

}
