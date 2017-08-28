package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;

/**
 * Abstract PointCut implementation for use with void-returning methods. See {@link AbstractPointCut}
 */
public abstract class VoidPointCut extends AbstractPointCut<Void> {
    public VoidPointCut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
    }

    /**
     * Called after method invocation
     * <p>
     * See {@link AbstractPointCut#after(Object)} ()}
     */
    public abstract void after();
    @Override
    public final Void after(Void v) {
        after();
        return null;
    }

    /**
     * Called when the method invocation is skipped (i.e. when {@link AbstractPointCut#before()} returns <code>true</code>)s
     * <p>
     * See {@link AbstractPointCut#onSkip()}
     */
    public abstract void skip();
    @Override
    public final Void onSkip() {
        skip();
        return null;
    }
}
