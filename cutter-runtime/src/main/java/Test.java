import org.x2a.cutter.annotation.Cut;

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


    public static void main(String[] args) {
        System.out.println("main");
        Test t = new Test();
        t.AMethod(42);
    }
}
