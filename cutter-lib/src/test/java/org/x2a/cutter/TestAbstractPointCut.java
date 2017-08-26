package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.pointcut.AbstractPointCut;

public class TestAbstractPointCut {

    static class PC extends AbstractPointCut<String> {

        public PC(JoinPoint joinPoint, Parameter[] parameters) {
            super(joinPoint, parameters);
        }

        @Override
        public boolean before() {
            Class<?>[] classes = getParameterTypes();
            Assert.assertArrayEquals(new Class<?>[]{String.class, int.class, float.class}, classes);
            return false;
        }

        @Override
        public String after(String returnValue) {
            return null;
        }

        @Override
        public String onSkip() {
            return "method skipped";
        }
    }

    @Cut(PC.class)
    public String method(String s, int i, float f) {
        return "return";
    }


    @Test
    public void testGetParameterTypes() {
        Assert.assertEquals("method skipped", method("", 0, 0.0f));
    }
}
