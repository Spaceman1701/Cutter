import org.x2a.cutter.annotation.Cut;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Test {



    @Cut(ACut.class)
    public Object AMethod(int i) {
        //System.out.println(i);
        //System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
        return "this is AMethods return!";
    }

    private void anotherMethod() {
    }


    public static void main(String[] args) throws Exception {
        System.out.println("main");
        Test t = new Test();
        System.out.println(t.AMethod(43));

        Method wrapped = Test.class.getDeclaredMethod("__wrapped__AMethod", int.class);
        System.out.println(wrapped);

        Method upwrapped = Test.class.getDeclaredMethod("AMethod", int.class);
        System.out.println(upwrapped);

    }
}
