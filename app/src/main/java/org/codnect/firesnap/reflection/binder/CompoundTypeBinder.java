package org.codnect.firesnap.reflection.binder;

import java.lang.reflect.Type;

/**
 * Created by Burak Koken on 8.9.2018.
 *
 * @author Burak Koken
 */
public class CompoundTypeBinder implements TypeBinder {

    private TypeBinder xTypeBinder;
    private TypeBinder yTypeBinder;
    private int hashCode;

    private CompoundTypeBinder(TypeBinder xTypeBinder, TypeBinder yTypeBinder) {
        this.xTypeBinder = xTypeBinder;
        this.yTypeBinder = yTypeBinder;
        hashCode = computeHashCode();
    }

    /**
     *
     * @param xTypeBinder
     * @param yTypeBinder
     * @return
     */
    public static TypeBinder create(TypeBinder xTypeBinder, TypeBinder yTypeBinder) {
        if (xTypeBinder == IdentityTypeBinder.getInstance())
            return xTypeBinder;
        if (yTypeBinder == IdentityTypeBinder.getInstance())
            return yTypeBinder;
        return new CompoundTypeBinder(xTypeBinder, yTypeBinder);
    }

    /**
     *
     * @param type
     * @return
     */
    @Override
    public Type bind(Type type) {
        return xTypeBinder.bind(yTypeBinder.bind(type));
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CompoundTypeBinder)) {
            return false;
        }
        CompoundTypeBinder anotherCompoundTypeBinder = (CompoundTypeBinder) obj;
        if(hashCode != anotherCompoundTypeBinder.hashCode()) {
            return false;
        }

        if(!xTypeBinder.equals(anotherCompoundTypeBinder.xTypeBinder)) {
            return false;
        }

        return yTypeBinder.equals(anotherCompoundTypeBinder.yTypeBinder);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     *
     * @return
     */
    private int computeHashCode() {
        int result;
        result = xTypeBinder.hashCode();
        result = 29 * result + yTypeBinder.hashCode();
        return result;
    }

}
