package org.x2a.cutter.cut;

import org.x2a.cutter.Constants;

/**
 * Contains information about the Joinpoint where a Pointcut was invoked. This includes the Class and method names.
 */
public class JoinPoint {

    private final Class<?> clazz;
    private final String methodName;
    private final String realMethodName;

    public JoinPoint(Class<?> clazz, String methodName) {
        this.clazz = clazz;
        this.methodName = methodName;
        this.realMethodName = Constants.METHOD_WRAPPED_PREFIX + methodName;
    }

    /**
     * Get the class this join point was created in.
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Get the name of the method that was intercepted at this Joinpoint.
     */
    public String getMethodName() {
        return methodName;
    }

    @Override
    public String toString() {
        return clazz + "::" + methodName;
    }

    /**
     * Get the name of the intercepting method. (This may be useful for reflection).
     */
    public String getRealMethodName() {
        return realMethodName;
    }

    @Override
    public int hashCode() {
        return 37 * (methodName.hashCode() + clazz.hashCode());
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass().equals(this.getClass())) {
            JoinPoint otherJp = (JoinPoint) other;
            return this.methodName.equals(otherJp.methodName) && this.clazz.equals(otherJp.clazz);
        }
        return false;
    }
}
