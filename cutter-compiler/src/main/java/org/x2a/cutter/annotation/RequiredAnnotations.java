package org.x2a.cutter.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.CLASS)
public @interface RequiredAnnotations {
    Class<? extends Annotation>[] value();
}
