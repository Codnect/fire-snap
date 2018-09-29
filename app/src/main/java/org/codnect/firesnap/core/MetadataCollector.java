package org.codnect.firesnap.core;

import org.codnect.firesnap.annotation.Embeddable;
import org.codnect.firesnap.annotation.MappedSuperClass;
import org.codnect.firesnap.annotation.Model;
import org.codnect.firesnap.exception.DuplicateMappingException;
import org.codnect.firesnap.exception.MappingException;
import org.codnect.firesnap.mapping.DenormalizedNode;
import org.codnect.firesnap.mapping.Node;
import org.codnect.firesnap.mapping.PersistentClass;
import org.codnect.firesnap.reflection.XClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public class MetadataCollector {

    private Map<String, String> modelAliasNames;
    private Map<String, PersistentClass> modelBindingMap;
    private Map<String, AnnotatedClassType> annotatedClassTypeMap;
    private Map<String, ModelNodeReference> modelNodeReferenceMap;
    private Map<String, Node> nodeMap;

    public MetadataCollector() {
        modelAliasNames = new HashMap<>();
        modelBindingMap = new HashMap<>();
        annotatedClassTypeMap = new HashMap<>();
        modelNodeReferenceMap = new HashMap<>();
        nodeMap = new HashMap<>();
    }

    /**
     * Add the alias names for model names.
     *
     * @param aliasName alias name for model name
     * @param modelName model name that will be added alias name
     */
    public void addModelAliasName(String aliasName, String modelName) {

        if(aliasName == null || modelName == null) {
            throw new IllegalArgumentException("Alias name or model name is null.");
        }

        modelAliasNames.put(aliasName, modelName);
    }

    /**
     * Add the persistence classes that is completed of the binding
     * process to model binding map.
     *
     * @param persistentClass persistence class
     */
    public void addModelBinding(PersistentClass persistentClass) {
        String modelName = persistentClass.getModelName();

        if(modelBindingMap.containsKey(modelName)) {
            throw new DuplicateMappingException(modelName + " is duplicated.");
        }

        modelBindingMap.put(modelName, persistentClass);
    }

    /**
     *
     * @param modelName
     * @return
     */
    public PersistentClass getModelBinding(String modelName) {
        return modelBindingMap.get(modelName);
    }

    /**
     *
     * @param xClass
     * @return
     */
    public AnnotatedClassType getClassType(XClass xClass) {
        AnnotatedClassType annotatedClassType = annotatedClassTypeMap.get(xClass);
        if(annotatedClassType == null) {
            if(xClass.isAnnotationPresent(Model.class)) {
                annotatedClassType = AnnotatedClassType.MODEL;
            } else if(xClass.isAnnotationPresent(Embeddable.class)) {
                annotatedClassType = AnnotatedClassType.EMBEDDABLE;
            } else if(xClass.isAnnotationPresent(MappedSuperClass.class)) {
                annotatedClassType = AnnotatedClassType.EMBEDDABLE_SUPERCLASS;
            } else {
                annotatedClassType = AnnotatedClassType.NONE;
            }
            annotatedClassTypeMap.put(xClass.getName(), annotatedClassType);
        }
        return annotatedClassType;
    }

    /**
     *
     * @param modelName
     * @param nodeName
     * @param node
     * @param superModelNodeReference
     * @return
     */
    public ModelNodeReference addModelNodeReference(String modelName,
                                                    String nodeName,
                                                    Node node,
                                                    ModelNodeReference superModelNodeReference) {
        ModelNodeReference modelNodeReference = new ModelNodeReference(
                nodeName,
                node,
                superModelNodeReference
        );
        modelNodeReferenceMap.put(modelName, modelNodeReference);
        return modelNodeReference;
    }

    /**
     *
     * @param modelName
     * @return
     */
    public ModelNodeReference getModelNodeReference(String modelName) {
        return modelNodeReferenceMap.get(modelName);
    }

    /**
     *
     * @param nodeName
     * @return
     */
    public Node addNode(String nodeName) {
        Node node = getNode(nodeName);
        if(node != null) {
            throw new DuplicateMappingException("Mapped already a node for specified node name");
        }
        node = new Node(nodeName);
        nodeMap.put(nodeName, node);
        return node;
    }

    /**
     *
     * @param nodeName
     * @return
     */
    public Node addDenormalizedNode(String nodeName, Node superModelNode) {
        Node node = getNode(nodeName);
        if(node != null) {
            throw new DuplicateMappingException("Mapped already a node for specified node name");
        }
        node = new DenormalizedNode(nodeName, superModelNode);
        nodeMap.put(nodeName, node);
        return node;
    }

    /**
     *
     * @param nodeName
     * @return
     */
    public Node getNode(String nodeName) {
        return nodeMap.get(nodeName);
    }

    /**
     *
     * @return
     */
    public Collection<Node> getAllNodes() {
        return nodeMap.values();
    }

}
