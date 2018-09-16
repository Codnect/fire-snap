package org.codnect.firesnap.util;

/**
 * Created by Burak Koken on 9.9.2018.
 *
 * @author Burak Koken
 */
public abstract class Pair<K, V> {

    private K key;
    private V value;
    private int hashCode;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
        this.hashCode = computeHashCode();
    }

    /**
     * Get the key for pair.
     *
     * @return the key for pair
     */
    public K getKey() {
        return key;
    }

    /**
     * Get the value for pair.
     *
     * @return the value for pair
     */
    public V getValue() {
        return value;
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

        if (!(obj instanceof Pair)) {
            return false;
        }

        Pair other = (Pair) obj;
        if(hashCode != other.hashCode()) {
            return false;
        }

        return compareObjects(key, other.key) && compareObjects(value, other.value);
    }

    /**
     * Get the hash code value for this.
     *
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Compute the hash code for this.
     *
     * @return hash code value
     */
    private int computeHashCode() {
        int result = 0;
        if(key != null) {
            result = key.hashCode();
        }
        if(value != null) {
            result = result ^ value.hashCode();
        }
        return result;
    }

    /**
     * Compare two objects, if they are same, it returns true.
     * Otherwise, it returns false.
     *
     * @param o1 first object
     * @param o2 second object
     * @return if they are same, it returns true. Otherwise,
     * it returns false.
     */
    private boolean compareObjects(Object o1, Object o2) {
        if(o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

}
