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
        Integer i = 0;
        System.out.println((int) i);
    }
}
