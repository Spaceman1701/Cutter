import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.pointcut.AbstractPointCut;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Test {



    @Cut(ACut.class)
    public Object AMethod(java.lang.String i, AbstractPointCut pointCut) {
        //System.out.println(i);
        //System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
        return "this is AMethods return!";
    }

    private void anotherMethod() {
    }


    public static void main(String[] args) throws Exception {
        Test t = new Test();
        System.out.println(t.AMethod("hello", new ACut(null, null)));
        AbstractPointCut pc = new AbstractPointCut(null, null) {
            @Override
            public boolean before() {
                return false;
            }

            @Override
            public Object after(Object returnValue) {
                return null;
            }

            @Override
            public Object onSkip() {
                return null;
            }
        };
    }
}
