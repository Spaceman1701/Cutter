package org.x2a.cutter.cut;

/**
 * Marker interface... or not
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
    public abstract RETURN_TYPE after();

    /**
     * Called iff {@link PointCut#before()} returns <code>false</code>. Use this method to add a default return value
     * if required.
     * @return The value to be returned by the method call
     */
    public abstract RETURN_TYPE onSkip();
}
