import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.Action;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.PointCut;

import java.util.Arrays;

public class Test {


    public static class ACut extends PointCut<Object> {
//        public ACut(JoinPoint joinPoint) {
//            super(joinPoint);
//        }
        public ACut() {
            System.out.println("ACut was created!");
        }

        @Override
        public Action before(Object[] args) {
            return null;
        }

        @Override
        public Object after(Object[] args) {
            return null;
        }
    }


    @Cut(ACut.class)
    public void AMethod() {
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
