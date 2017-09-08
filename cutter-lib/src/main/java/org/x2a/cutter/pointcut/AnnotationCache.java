package org.x2a.cutter.pointcut;

import org.x2a.cutter.cut.JoinPoint;

import java.lang.annotation.Annotation;

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
            return joinPoint.hashCode() + annotationClass.hashCode();
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

    private AnnotationCache() {

    }
}
