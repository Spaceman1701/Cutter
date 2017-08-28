package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;

import java.lang.reflect.Method;

public class TestStaticCut {

    @Cut(TestCut.TestPC.class) //This also tests compound access to Pointcut classes
    private static String methodToCut(String s) {
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
