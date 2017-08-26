package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

import java.lang.annotation.Target;

public class TestCut {

    static class TestPC extends PointCut<Boolean> {
        public TestPC(JoinPoint joinPoint, Parameter[] parameters) {
            super(joinPoint, parameters);
        }

        @Override
        public boolean before() {
            return parameters[0].equals("invoke");
        }

        @Override
        public Boolean after(Boolean aBoolean) {
            return true;
        }

        @Override
        public Boolean onSkip() {
            return true;
        }
    }


    @Cut(TestPC.class)
    private boolean methodToCut(String s) {
        return false;
    }


    @Test
    public void testCut() {
        Assert.assertTrue("the cut should've returned true", methodToCut("invoke"));
    }

    @Test
    public void testOnSkip() {
        Assert.assertTrue("onSkip should've been called and should've returned true", methodToCut("no"));
    }
}
