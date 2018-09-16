package org.codnect.firesnap.reflection.binder;

import java.lang.reflect.Type;

/**
 * CompoundTypeBinder provides type binding using two different
 * type binder.
 *
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
     * Create a new compound type binder using two different type binder.
     *
     * @param xTypeBinder first type binder
     * @param yTypeBinder second type binder
     * @return a composition of two type binder
     */
    public static TypeBinder create(TypeBinder xTypeBinder, TypeBinder yTypeBinder) {
        if (xTypeBinder == IdentityTypeBinder.getInstance())
            return xTypeBinder;
        if (yTypeBinder == IdentityTypeBinder.getInstance())
            return yTypeBinder;
        return new CompoundTypeBinder(xTypeBinder, yTypeBinder);
    }

    /**
     * Bind the specified type.
     *
     * @param type an instance of type
     * @return a new type for specified type.
     */
    @Override
    public Type bind(Type type) {
        return xTypeBinder.bind(yTypeBinder.bind(type));
    }

    /**
     * Compare this object to another object, if they are
     * same, it returns true. Otherwise, it returns false.
     *
     * @param obj another object
     * @return if they are same, it returns true. Otherwise,
     * it returns false.
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
     * Get the hash code value for this type binder.
     *
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Compute the hash code for this type binder.
     *
     * @return hash code value
     */
    private int computeHashCode() {
        int result;
        result = xTypeBinder.hashCode();
        result = 29 * result + yTypeBinder.hashCode();
        return result;
    }

}
