package org.codnect.firesnap.reflection.binder;

import java.lang.reflect.Type;

/**
 * Created by Burak Koken on 8.9.2018.
 *
 * @author Burak Koken
 */
public class IdentityTypeBinder implements TypeBinder{

    private static final IdentityTypeBinder identityTypeBinder = new IdentityTypeBinder();

    private IdentityTypeBinder() {

    }

    /**
     *
     * @return
     */
    public static IdentityTypeBinder getInstance() {
        return identityTypeBinder;
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public Type bind(Type type) {
        return type;
    }

}
