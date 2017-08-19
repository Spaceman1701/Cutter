package org.x2a.cutter.annotation;

import org.x2a.cutter.cut.PointCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Cut {
    Class<? extends PointCut> value();
}
