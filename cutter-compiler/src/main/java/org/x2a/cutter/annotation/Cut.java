package org.x2a.cutter.annotation;

import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Cut {
    Class<? extends PointCut> value();

    final class Utils { //this class exists to solve potential import problems
        public static Parameter wrapParameter(final String name, final Class<?> clazz, final Object val) {
            return new Parameter(name, clazz, val);
        }

        /**
         * @param values {"name1", clazz1, val1, "name2", clazz2, val2}
         */
        public static Parameter[] toParameters(Object[] values) {
            Parameter[] parameters = new Parameter[values.length / 3];
            for (int i = 0; i < values.length; i += 3) {
                String name = (String) values[i];
                Class<?> clazz = (Class<?>) values[i + 1];
                Object val = values[i + 2];
                parameters[i / 3] = new Parameter(name, clazz, val);
            }
            return parameters;
        }

        public static JoinPoint createJoinPoint(Class<?> clazz, String methodName) {
            return new JoinPoint(clazz, methodName);
        }
    }
}
