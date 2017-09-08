package org.x2a.cutter.annotation;

import java.lang.annotation.*;

/**
 * Applies to {@link org.x2a.cutter.cut.Advice} class definitions.
 * <p>
 * Define annotations required to be present whenever the targeted Advice is used. Requirements are verified at compile-time
 */
@Documented @Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RequiredAnnotations {
    Class<? extends Annotation>[] value();
}
