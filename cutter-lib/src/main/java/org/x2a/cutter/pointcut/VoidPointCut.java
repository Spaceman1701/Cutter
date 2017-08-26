package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

public abstract class VoidPointCut extends AbstractPointCut<Void> {
    public VoidPointCut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
    }

    public abstract void after();
    @Override
    public Void after(Void v) {
        after();
        return null;
    }

    public abstract void skip();
    @Override
    public Void onSkip() {
        skip();
        return null;
    }
}
