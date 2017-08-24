import org.x2a.cutter.annotation.Cut;

import java.util.Arrays;

public class Test {



    @Cut(ACut.class)
    public void AMethod() {
        Object o = null;
        System.out.println(o);
        int i = 6;
        System.out.println(i);
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
    }

    private void anotherMethod() {
    }


    public static void main(String[] args) {
        System.out.println("main");
        Test t = new Test();
        t.AMethod();
    }
}
