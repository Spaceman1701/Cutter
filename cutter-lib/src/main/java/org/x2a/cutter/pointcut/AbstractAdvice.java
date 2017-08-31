package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.Advice;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of {@link Advice}. Adds useful utility methods.
 * @param <RETURN_TYPE> THe return type of functions targeted by this Advice.
 */
public abstract class AbstractAdvice<RETURN_TYPE> extends Advice<RETURN_TYPE> {

    /**
     * All implementations <b>must</b> have a constructor with this signature.
     * @param joinPoint The joinPoint information (class and method names)
     * @param parameters the targeted method's parameters
     */
    public AbstractAdvice(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
    }

    /**
     * Get a parameter by its declared name
     */
    protected Parameter getParameter(String name) {
        for (int i = 0; i < parameterCount(); i++) {
            if (getParameter(i).getName().equals(name)) {
                return getParameter(i);
            }
        }
        return null;
    }

    /**
     * get a parameter value with a known clazz type
     * @param clazz the type of the parameter
     * @param name the name of the parameter
     */
    @SuppressWarnings("unchecked")
    protected <T> T getParameterValue(Class<? extends T> clazz, String name) {
        for (int i = 0; i < parameterCount(); i++) {
            Parameter p = getParameter(i);
            if (p.getClazz().equals(clazz) && p.getName().equals(name)) {
                return (T) p.getCurrentValue();
            }
        }
        return null;
    }

    /**
     * Get the list of parameters with the given type
     * @param clazz the class type of the parameters
     * @return the list of parameters
     */
    protected List<Parameter> getParameters(Class<?> clazz) {
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < parameterCount(); i++) {
            if (getParameter(i).getClazz().equals(clazz)) {
                parameters.add(getParameter(i));
            }
        }
        return parameters;
    }

    /**
     * Get an array of the parameter types
     * @return An array of parameter types in the same order as the parameter array
     */
    protected Class<?>[] getParameterTypes() {
        Class<?>[] types = new Class[parameterCount()];
        for (int i = 0; i < parameterCount(); i++) {
            types[i] = getParameter(i).getClazz();
        }
        return types;
    }

    /**
     * Get an annotation present on the targeted method - use this for Pointcut parameters
     * @param annotation the annotation class
     * @return The annotation, or <code>null</code> if not present
     */
    protected <A extends Annotation> A getMethodAnnotation(Class<A> annotation) {
        try {
            Method method = joinPoint.getClazz().getDeclaredMethod(joinPoint.getMethodName(), getParameterTypes());
            return method.getDeclaredAnnotation(annotation);
        } catch (NoSuchMethodException e) { //this should not ever happen
            throw new RuntimeException(e); //TODO: this really shouldn't happen
        }
    }

}
