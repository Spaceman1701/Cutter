package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.pointcut.AbstractPointCut;

import java.lang.reflect.Method;

public class TestAnonClass {

    static class TestPC extends AbstractPointCut<String> {
        public TestPC(JoinPoint joinPoint, Parameter[] parameters) {
            super(joinPoint, parameters);
        }

        @Override
        public boolean before() {
            return true;
        }

        @Override
        public String after(String aBoolean) {
            return ((Foo) getParameter("f").getOriginalValue()).doFoo();
        }

        @Override
        public String onSkip() {
            return "skipped";
        }
    }

    interface Foo {
        String doFoo();
    }

    @Cut(TestPC.class)
    private String methodToCut(Foo f) {
        return "real method return";
    }


    @Test
    public void testCutAnonClassSyntax() {
        Assert.assertEquals("the cut should've returned true", "this is an internal foo!", methodToCut(new Foo() {
            @Override
            public String doFoo() {
                return "this is an internal foo!";
            }
        }));
    }

    @Test
    public void testCutLamdaSyntax() {
        Assert.assertEquals("the cut should've returned true", "this is an internal foo!",
                methodToCut(() -> "this is an internal foo!"));
    }

    @Test
    public void testWrappedMethodExists() throws Exception {
        Method method = getClass().getDeclaredMethod("__wrapped__methodToCut", Foo.class);
        Assert.assertNotNull(method);
        Assert.assertFalse(method.isAccessible());
    }
}
