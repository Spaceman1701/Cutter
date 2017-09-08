package org.x2a.cutter.annotation;

import java.lang.annotation.*;

@Documented @Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RequiredAnnotations {
    Class<? extends Annotation>[] value();
}
