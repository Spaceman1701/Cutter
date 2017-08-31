package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;

/**
 * Abstract Advice implementation for use with void-returning methods. See {@link AbstractAdvice}
 */
public abstract class VoidAdvice extends AbstractAdvice<Void> {
    public VoidAdvice(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
    }

    /**
     * Called after method invocation
     * <p>
     * See {@link AbstractAdvice#after(Object)} ()}
     */
    public abstract void after();
    @Override
    public final Void after(Void v) {
        after();
        return null;
    }

    /**
     * Called when the method invocation is skipped (i.e. when {@link AbstractAdvice#before()} returns <code>true</code>)s
     * <p>
     * See {@link AbstractAdvice#onSkip()}
     */
    public abstract void skip();
    @Override
    public final Void onSkip() {
        skip();
        return null;
    }
}
