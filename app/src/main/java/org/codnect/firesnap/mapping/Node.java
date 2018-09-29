package org.codnect.firesnap.mapping;

/**
 * Created by Burak Koken on 29.9.2018.
 *
 * @author Burak Koken
 */
public class Node {

    private String name;
    private boolean isAbstract;

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

}
