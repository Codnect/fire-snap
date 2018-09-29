package org.codnect.firesnap.binder;

import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.core.InheritanceState;
import org.codnect.firesnap.core.MetadataContext;
import org.codnect.firesnap.core.ModelNodeReference;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.mapping.Node;
import org.codnect.firesnap.mapping.PersistentClass;
import org.codnect.firesnap.mapping.SingleNodeSubclass;
import org.codnect.firesnap.reflection.XClass;
import org.codnect.firesnap.util.StringHelper;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class ModelBinder {

    private String name;
    private XClass annotatedClass;
    private Model modelAnnotation;
    private MetadataContext metadataContext;
    private PersistentClass persistentClass;
    private InheritanceState inheritanceState;
    private String discriminatorValue;

    public ModelBinder(XClass annotatedClass, Model modelAnnotation, PersistentClass persistentClass,
                       MetadataContext metadataContext) {
        this.annotatedClass = annotatedClass;
        this.modelAnnotation = modelAnnotation;
        this.persistentClass = persistentClass;
        this.metadataContext = metadataContext;
    }

    /**
     * Bind the model.
     */
    public void bindModel() {
        if(BinderHelper.isEmptyAnnotationValue(modelAnnotation.value())) {
            persistentClass.setAliasName(annotatedClass.getName());
        }
        else {
            name = modelAnnotation.value();
            persistentClass.setAliasName(name);
        }
        persistentClass.setModelName(annotatedClass.getName());
        persistentClass.setClassName(annotatedClass.getName());
        persistentClass.setAbstract(annotatedClass.isAbstract());

        String aliasName = persistentClass.getAliasName();
        String modelName = persistentClass.getModelName();
        metadataContext.getMetadataCollector().addModelAliasName(modelName, modelName);
        if(!modelName.equals(aliasName)) {
            metadataContext.getMetadataCollector().addModelAliasName(aliasName, modelName);
        }
    }

    /**
     *
     * @return
     */
    public InheritanceState getInheritanceState() {
        return inheritanceState;
    }

    /**
     *
     * @param inheritanceState
     */
    public void setInheritanceState(InheritanceState inheritanceState) {
        this.inheritanceState = inheritanceState;
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
     * @param nodeName
     * @param superModelNodeReference
     */
    public void bindNode(String nodeName, ModelNodeReference superModelNodeReference) {
        String modelNodeName;
        if(!StringHelper.isEmpty(nodeName)) {
            modelNodeName = nodeName;
        } else if(!StringHelper.isEmpty(name)) {
            modelNodeName = name;
        } else {
            modelNodeName = persistentClass.getModelName();
        }
        Node node = NodeBinder.bindNode(
                modelNodeName,
                superModelNodeReference,
                persistentClass.isAbstract(),
                metadataContext);
        metadataContext.getMetadataCollector().addModelNodeReference(
                persistentClass.getModelName(),
                modelNodeName,
                node,
                superModelNodeReference);
        if(persistentClass.isNodeOwner()) {
            persistentClass.setNode(node);
        } else {
            throw new MappingException("You cannot bind a node for subclasses that have the single node strategy : "
                    + persistentClass.getClassName());
        }
    }

    /**
     *
     * @param superModelNodeReference
     */
    public void bindNodeForSingleNodeStrategySubClass(ModelNodeReference superModelNodeReference) {
        if(!(persistentClass instanceof SingleNodeSubclass)) {
            throw new MappingException("This class is not a SingleNodeSubclass instance : " + persistentClass.getClassName());
        }
        metadataContext.getMetadataCollector().addModelNodeReference(
                persistentClass.getModelName(),
                superModelNodeReference.getNodeName(),
                superModelNodeReference.getNode(),
                superModelNodeReference
        );
    }

}
