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

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

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

    @Override
    public int hashCode() {
        return hashCode;
    }

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

    private boolean compareObjects(Object o1, Object o2) {
        if(o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }
}
