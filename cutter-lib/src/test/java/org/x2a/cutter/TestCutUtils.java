package org.x2a.cutter;

import org.junit.Assert;
import org.junit.Test;
import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;

public class TestCutUtils {

    @Test
    public void testCreateParameterArray() {
        Object[] objects = new Object[]{"x", int.class, 9, "y", Integer.class, 12, "str", String.class, "foobar"};

        Parameter[] parameters = Cut.Utils.toParameters(objects);
        Assert.assertEquals(3, parameters.length);

        Assert.assertEquals(int.class, parameters[0].getClazz());
        Assert.assertEquals(Integer.class, parameters[1].getClazz());
        Assert.assertEquals(String.class, parameters[2].getClazz());

        Assert.assertEquals("x", parameters[0].getName());
        Assert.assertEquals("y", parameters[1].getName());
        Assert.assertEquals("str", parameters[2].getName());

        Assert.assertEquals(9, parameters[0].getOriginalValue());
        Assert.assertEquals(12, parameters[1].getOriginalValue());
        Assert.assertEquals("foobar", parameters[2].getOriginalValue());

        Assert.assertEquals(parameters[0].getCurrentValue(), parameters[0].getOriginalValue());
    }


    @Test
    public void testCreateJoinPoint() {
        JoinPoint jp = Cut.Utils.createJoinPoint(this.getClass(), "test");
        Assert.assertNotNull(jp);

        Assert.assertEquals(this.getClass(), jp.getClazz());
        Assert.assertEquals("test", jp.getMethodName());
        Assert.assertEquals(Constants.METHOD_WRAPPED_PREFIX + "test", jp.getRealMethodName());
    }
}
