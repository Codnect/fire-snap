package org.codnect.firesnap.inheritance;

import org.codnect.firesnap.core.MetadataContext;

/**
 * Created by Burak Koken on 20.5.2018.
 *
 * @author Burak Koken
 */
public abstract class Subclass extends PersistentClass {

    private PersistentClass superModel;

    public Subclass(MetadataContext metadataContext, PersistentClass superModel) {
        super(metadataContext);
        this.superModel = superModel;
    }

}
