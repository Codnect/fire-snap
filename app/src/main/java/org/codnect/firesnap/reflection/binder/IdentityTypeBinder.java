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
     * Get the instance of the IdentityTypeBinder.
     *
     * @return the instance of the IdentityTypeBinder
     */
    public static IdentityTypeBinder getInstance() {
        return identityTypeBinder;
    }

    /**
     * Bind the specified type.
     *
     * @param type an instance of type
     * @return a new type for specified type.
     */
    @Override
    public Type bind(Type type) {
        return type;
    }

}
