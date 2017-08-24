package org.x2a.cutter.cut;

import java.lang.reflect.Type;

public class Parameter {
    private final String name;
    private final Class<?> type;
    private final Object originalValue;

    private Object currentValue;

    public Parameter(final String name, final Class<?> type, final Object value) {
        this.name = name;
        this.type = type;
        this.originalValue = value;

        this.currentValue = value;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getCurrentValue() {
        return currentValue;
    }

    public Object getOriginalValue() {
        return originalValue;
    }
}
