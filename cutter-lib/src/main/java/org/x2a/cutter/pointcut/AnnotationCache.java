package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.JoinPoint;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;

public final class AnnotationCache {

    private static final AnnotationCache INSTANCE = new AnnotationCache();

    private static final class CacheKey {
        private final JoinPoint joinPoint;
        private final Class<? extends Annotation> annotationClass;

        private CacheKey(JoinPoint joinPoint, Class<? extends Annotation> annotationClass) {
            this.joinPoint = joinPoint;
            this.annotationClass = annotationClass;
        }

        @Override
        public int hashCode() {
            return 37 * (joinPoint.hashCode() + annotationClass.hashCode());
        }

        @Override
        public boolean equals(Object other) {
            if (other != null && other.getClass().equals(CacheKey.class)) {
                CacheKey otherKey = (CacheKey) other;
                return otherKey.joinPoint.equals(joinPoint) && otherKey.annotationClass.equals(annotationClass);
            }
            return false;
        }
    }

    private final ConcurrentHashMap<CacheKey, Annotation> annotations;

    private AnnotationCache() {
        annotations = new ConcurrentHashMap<>();
    }

    void put(JoinPoint jp, Class<? extends Annotation> clazz, Annotation annotation) {
        annotations.put(new CacheKey(jp, clazz), annotation);
    }

    @SuppressWarnings("unchecked")
    <A extends Annotation> A get(JoinPoint jp, Class<A> annotation) {
        return (A) annotations.get(new CacheKey(jp, annotation));
    }

    public static AnnotationCache getInstance() {
        return INSTANCE;
    }
}
