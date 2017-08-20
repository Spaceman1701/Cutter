import org.x2a.cutter.annotation.Cut;
import org.x2a.cutter.cut.Action;
import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.PointCut;

public class Test {


    public static class ACut extends PointCut<Object> {
        public ACut(JoinPoint joinPoint) {
            super(joinPoint);
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
        System.out.println("hello");
    }


    public static void main(String[] args) {
        System.out.println("main");
        Test t = new Test();
        t.AMethod2();
        t.AMethod();
    }
}
