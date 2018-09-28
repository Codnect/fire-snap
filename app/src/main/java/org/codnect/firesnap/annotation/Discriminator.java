package org.codnect.firesnap.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Burak Koken on 28.9.2018.
 *
 * @author Burak Koken
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Discriminator {

    String name() default "DISCTYPE";

    DiscriminatorType discriminatorType() default DiscriminatorType.STRING;

}
