package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.pointcut.AbstractPointCut;

import java.lang.reflect.Method;

@Ignore //TODO: Currently no good solution to this... Anon classes are currently unsupported
public class TestCutFromAnonClass {
    static class TestPC extends AbstractPointCut<String> {
        public TestPC(JoinPoint joinPoint, Parameter[] parameters) {
            super(joinPoint, parameters);
        }

        @Override
        public boolean before() {
            return getParameterValue(0).equals("invoke");
        }

        @Override
        public String after(String aBoolean) {
            return "after";
        }

        @Override
        public String onSkip() {
            return "skipped";
        }
    }

    interface Foo {
        String methodToCut(String s);
    }

    private Foo instance = new Foo() {

        @Override
        @Cut(TestCut.TestPC.class)
        public String methodToCut(String s) {
            return "the real return";
        }
    };



    @Test
    public void testCut() {
        Assert.assertEquals("the cut should've returned true", "after", instance.methodToCut("invoke"));
    }

    @Test
    public void testOnSkip() {
        Assert.assertEquals("onSkip should've been called and should've returned true", "skipped", instance.methodToCut("no"));
    }

    @Test
    public void testWrappedMethodExists() throws Exception {
        Method method = instance.getClass().getDeclaredMethod("__wrapped__methodToCut", String.class);
        Assert.assertNotNull(method);
        Assert.assertFalse(method.isAccessible());
    }
}
