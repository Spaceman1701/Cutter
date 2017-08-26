package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

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

}
