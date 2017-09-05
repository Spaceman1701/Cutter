package org.x2a.cutter.annotation;

import java.lang.annotation.Annotation;

public @interface RequiredAnnotations {
    Class<? extends Annotation>[] value();
}
