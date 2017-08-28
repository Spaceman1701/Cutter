package org.x2a.example.cacheable.cahce;

import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.pointcut.AbstractPointCut;

public class Cacheable extends AbstractPointCut<Object> {

    private final String cacheKey;

    public Cacheable(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
        cacheKey = getCacheKey();
    }

    /**
     * Get the Pointcut parameters from the CacheParams annotation
     */
    private String getCacheKey() {
        CacheParams params = getMethodAnnotation(CacheParams.class);
        String keyParam = params.key();
        Parameter p = getParameter(keyParam);
        return (String) p.getOriginalValue();
    }

    @Override
    public boolean before() {
        return !SimpleCache.getInstance().contains(cacheKey); //if the key is not in the cache, run the method
    }

    @Override
    public Object after(Object returnValue) {
        SimpleCache.getInstance().put(cacheKey, returnValue); //put the value in the cache
        return returnValue;
    }

    @Override
    public Object onSkip() {
        return SimpleCache.getInstance().get(cacheKey); //we skipped so the value must be in the cache
    }
}
