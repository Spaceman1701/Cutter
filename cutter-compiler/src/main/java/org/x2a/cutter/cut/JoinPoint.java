package org.x2a.cutter.cut;

public class JoinPoint {

    private final Class<?> clazz;
    private final String methodName;

    public JoinPoint(Class<?> clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getMethodName() {
        return methodName;
    }
}
