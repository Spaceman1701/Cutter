package org.x2a.cutter.cut;

/**
 * Marker interface... or not
 */
public abstract class PointCut<RETURN_TYPE> {

    //protected final JoinPoint joinPoint;

    /**
     * Creates a PointCut instance
     * @param joinPoint the join point where this point cut was created
     */
//    public PointCut(final JoinPoint joinPoint) {
//        this.joinPoint = joinPoint;
//    }

    /**
     * Executed before the method the point cut is targeting is invoked (and before the stack frame is allocated)
     * @param args the arguments passed to the method this PointCut is wrapping
     * @return An Action - either INVOKE or SKIP. If INVOKE, the method will be invoked. If skip, the method call will be skipped.
     * Skipping a method will cause the {@link PointCut#after(Object[])} method to not be called.
     */
    public abstract Action before(Object[] args);

    /**
     * Executed after the method the PointCut targets is called (and after the stack frame is cleared)
     * @param args the arguments passed to the method (including any modifications made by {@link PointCut#before(Object[])}
     * @return The value to be returned by the method call. Use {@link Void} for void methods. The return of this method will
     * undergo a static cast to the return type of the method being wrapped.
     */
    public abstract RETURN_TYPE after(Object[] args);
}
