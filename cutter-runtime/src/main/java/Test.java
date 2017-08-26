import org.x2a.cutter.annotation.Cut;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Test {



    @Cut(ACut.class)
    public void AMethod(int i) {
        Object[] array = new Object[]{"hello", "world", 2};
        int two = (int) array[2];
        System.out.println(int.class);
        System.out.println(two);
        System.out.println(i);
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
    }

    private void anotherMethod() {
    }


    public static void main(String[] args) throws Exception {
        System.out.println("main");
        Test t = new Test();
        t.AMethod(42);

        Method wrapped = Test.class.getDeclaredMethod("__wrapped__AMethod", int.class);
        System.out.println(wrapped);

        Method upwrapped = Test.class.getDeclaredMethod("AMethod", int.class);
        System.out.println(upwrapped);

    }
}
