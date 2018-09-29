package org.codnect.firesnap.core;

import org.codnect.firesnap.mapping.Node;

/**
 * Created by Burak Koken on 29.9.2018.
 *
 * @author Burak Koken
 */
public class ModelNodeReference {

    private String nodeName;
    private Node node;
    private ModelNodeReference superModelNodeReference;

    public ModelNodeReference(String nodeName, Node node, ModelNodeReference superModelNodeReference) {
        this.nodeName = nodeName;
        this.node = node;
        this.superModelNodeReference = superModelNodeReference;
    }

    /**
     *
     * @return
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     *
     * @return
     */
    public Node getNode() {
        return node;
    }

    /**
     *
     * @return
     */
    public ModelNodeReference getSuperModelNodeReference() {
        return superModelNodeReference;
    }

}
