package org.x2a.cutter.cut;

/**
 * Method arguments are passed to the {@link Advice} as an array of Parameters. The Parameter object contains the parameter's
 * declared name, actual class, and original value (the value the parameter had when the method was called). The Parameter object
 * also contains the "current" value of the parameter, which can be mutated by a Pointcut.
 */
public class Parameter {
    private final String name;
    private final Class<?> clazz;
    private final Object originalValue;

    private Object currentValue;

    /**
     * Constructs a Parameter object. Used internally by Cutter.
     * @param name The parameter's declared name
     * @param type The parameter's class
     * @param value The parameter's value
     */
    public Parameter(final String name, final Class<?> type, final Object value) {
        this.name = name;
        this.clazz = type;
        this.originalValue = value;

        this.currentValue = value;
    }

    /**
     * Get the parameter's class. Primitive types will return primitive classes (e.g. int.class).
     * @return The class object corresponding to the parameter's class
     */
    public Class<?> getClazz() {
        return clazz;
    }

    /**
     * Get the parameter's declared name.
     * @return The parameter's name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the current (mutable) value for the parameter. See {@link Parameter#setCurrentValue(Object)}
     * @return the current value of the parameter
     */
    public Object getCurrentValue() {
        return currentValue;
    }

    /**
     * Set the current value of the parameter. If called in the {@link Advice#before()} method, the new value of this
     * parameter will be used when the intercepted method is executed. Calling this method does not change the result of
     * {@link Parameter#getOriginalValue()}
     * <p>
     * Java's boxing and type-casting rules apply to parameter values. Setting a parameter value to an illegal type <b>WILL</b>
     * cause a runtime exception
     * </p>
     * @param val The new value of the parameter.
     */
    public void setCurrentValue(Object val) {
        this.currentValue = val;
    }

    /**
     * Get the value of the parameter when the method was called
     * @return the original parameter value
     */
    public Object getOriginalValue() {
        return originalValue;
    }
}
