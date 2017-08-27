package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPointCut<RETURN_TYPE> extends PointCut<RETURN_TYPE> {

    public AbstractPointCut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
    }

    protected Parameter getParameter(String name) {
        for (int i = 0; i < parameterCount(); i++) {
            if (getParameter(i).getName().equals(name)) {
                return getParameter(i);
            }
        }
        return null;
    }

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

    protected List<Parameter> getParameters(Class<?> clazz) {
        List<Parameter> parameters = new ArrayList<>();
        for (int i = 0; i < parameterCount(); i++) {
            if (getParameter(i).getClazz().equals(clazz)) {
                parameters.add(getParameter(i));
            }
        }
        return parameters;
    }

    protected Class<?>[] getParameterTypes() {
        Class<?>[] types = new Class[parameterCount()];
        for (int i = 0; i < parameterCount(); i++) {
            types[i] = getParameter(i).getClazz();
        }
        return types;
    }

    protected Annotation getMethodAnnotation(Class<? extends Annotation> annotation) {
        try {
            Method method = joinPoint.getClazz().getDeclaredMethod(joinPoint.getMethodName(), getParameterTypes());
            return method.getDeclaredAnnotation(annotation);
        } catch (NoSuchMethodException e) { //this should not ever happen
            throw new RuntimeException(e); //TODO: this really shouldn't happen
        }
    }

}
