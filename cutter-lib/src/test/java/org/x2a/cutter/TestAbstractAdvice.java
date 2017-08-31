package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.pointcut.AbstractAdvice;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TestAbstractAdvice {

    static class PC extends AbstractAdvice<String> {

        public PC(JoinPoint joinPoint, Parameter[] parameters) {
            super(joinPoint, parameters);
        }

        @Override
        public boolean before() {
            Class<?>[] classes = getParameterTypes();
            Assert.assertArrayEquals(new Class<?>[]{String.class, int.class, float.class}, classes);
            return (int)getParameterValue(1) == 0;
        }

        @Override
        public String after(String returnValue) {
            return getMethodAnnotation(TestAnnotation.class).value();
        }

        @Override
        public String onSkip() {
            return "method skipped";
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {
        String value();
    }

    @TestAnnotation("this is a value")
    @Cut(PC.class)
    public String method(String s, int i, float f) {
        return "return";
    }


    @Test
    public void testGetParameterTypes() {
        Assert.assertEquals("method skipped", method("", 6, 0.0f));
    }

    @Test
    public void testGetOtherAnnotation() {
        Assert.assertEquals("this is a value", method("", 0, 0.0f));
    }
}
