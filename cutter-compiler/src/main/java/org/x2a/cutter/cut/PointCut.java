package org.x2a.cutter.cut;

/**
 * Marker interface... or not
 */
public abstract class PointCut<RETURN_TYPE> {

    protected final JoinPoint joinPoint;

    public PointCut(final JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    public abstract Action before(Object[] args);

    public abstract RETURN_TYPE after(Object[] args);
}
