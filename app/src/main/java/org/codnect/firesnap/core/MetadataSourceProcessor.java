package org.codnect.firesnap.core;

/**
 * Created by Burak Koken on 25.2.2018.
 *
 * @author Burak Koken
 */
public interface MetadataSourceProcessor {

    void prepare();

    void process();

    void finish();

}