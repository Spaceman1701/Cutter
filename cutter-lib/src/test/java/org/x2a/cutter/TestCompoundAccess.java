package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.pointcut.AbstractAdvice;

import java.lang.reflect.Method;

public class TestCompoundAccess {

    static class WrapperOne {
        static class WrapperTwo {
            static class WrapperThree {
                static class WrapperFour {
                    static class WrapperFive {
                        static class TestPC extends AbstractAdvice<String> {
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
                    }
                }
            }
        }
    }



    @Cut(WrapperOne.WrapperTwo.WrapperThree.WrapperFour.WrapperFive.TestPC.class)
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
