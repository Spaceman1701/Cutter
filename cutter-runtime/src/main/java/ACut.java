import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

public class ACut extends PointCut<Void> {
    //        public ACut(JoinPoint joinPoint) {
//            super(joinPoint);
//        }
    public ACut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
        System.out.println("ACut was created!");
    }

    @Override
    public boolean before() {
        System.out.println("before was called!");
        System.out.println("at jp: " + joinPoint.toString());
        return true;
    }

    @Override
    public Void after() {
        return null;
    }

    @Override
    public Void onSkip() {
        return null;
    }
}