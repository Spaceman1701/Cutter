package org.x2a.cutter.cut;

import org.x2a.cutter.Constants;

public class JoinPoint {

    private final Class<?> clazz;
    private final String methodName;
    private final String realMethodName;

    public JoinPoint(Class<?> clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.realMethodName = Constants.METHOD_WRAPPED_PREFIX + methodName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return clazz + "::" + methodName;
    }

    public String getRealMethodName() {
        return realMethodName;
    }
}
