package org.codnect.firesnap.util;

/**
 * Created by Burak Koken on 29.9.2018.
 *
 * @author Burak Koken
 */
public class StringHelper {

    /**
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    /**
     *
     * @param prefix
     * @param name
     * @return
     */
    public static String qualify(String prefix, String name) {
        if ( name == null || prefix == null ) {
            throw new IllegalArgumentException("Prefix or name parameters must not be null.");
        }
        return prefix + '.' + name;
    }

}
