package org.x2a.cutter;

import com.sun.source.tree.MethodTree;
import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

import javax.annotation.processing.AbstractProcessor;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class TestCut {

    static class TestPC extends PointCut<String> {
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


    @Cut(TestPC.class)
    private String methodToCut(String s) {
        return "the real return";
    }


    @Test
    public void testCut() {
        Assert.assertEquals("the cut should've returned true", "after", methodToCut("invoke"));
    }

    @Test
    public void testOnSkip() {
        Assert.assertEquals("onSkip should've been called and should've returned true", "skipped", methodToCut("no"));
    }

    @Test
    public void testWrappedMethodExists() throws Exception {
        Method method = getClass().getDeclaredMethod("__wrapped__methodToCut", String.class);
        Assert.assertNotNull(method);
        Assert.assertFalse(method.isAccessible());
    }
}
