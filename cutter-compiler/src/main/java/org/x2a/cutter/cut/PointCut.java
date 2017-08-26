package org.x2a.cutter.cut;

/**
 * Base interface for defining PointCut objects (see {@link org.x2a.cutter.annotation.Cut}).
 * <p>
 * The return type should be specified by the implementation. Use {@link Void} for void return types
 * </p>
 * <p>
 * Input parameters and return values follow Java's type casting and boxing rules
 * </p>
 */
public abstract class PointCut<RETURN_TYPE> {

    protected final JoinPoint joinPoint;
    protected final Parameter[] parameters;
    /**
     * Creates a PointCut instance
     * @param joinPoint the join point where this point cut was created
     */
    public PointCut(final JoinPoint joinPoint, Parameter[] parameters) {
        this.joinPoint = joinPoint;
        this.parameters = parameters;
    }

    /**
     * Gets the current value of the parameter at the given index. Used internally by Cutter. see {@link Parameter#getCurrentValue()}
     * @param index The parameter's index
     * @return the current value of the parameter
     */
    public final Object getParameterValue(int index) {
        return parameters[index].getCurrentValue();
    }

    /**
     * Executed before the method the point cut is targeting is invoked (and before the stack frame is allocated)
     * @return <code>true</code> iff the target method is to be executed. <code>false</code> otherwise
     */
    public abstract boolean before();

    /**
     * Executed after the method the PointCut targets is called (and after the stack frame is cleared)
     * @return The value to be returned by the method call. Use {@link Void} for void methods. The return of this method will
     * undergo a static cast to the return type of the method being wrapped.
     */
    public abstract RETURN_TYPE after(RETURN_TYPE returnValue);

    /**
     * Called iff {@link PointCut#before()} returns <code>false</code>. Use this method to add a default return value
     * if required.
     * @return The value to be returned by the method call
     */
    public abstract RETURN_TYPE onSkip();
}
