import org.x2a.cutter.cut.PointCut;

public class ACut extends PointCut<Void> {
    //        public ACut(JoinPoint joinPoint) {
//            super(joinPoint);
//        }
    public ACut() {
        System.out.println("ACut was created!");
    }

    @Override
    public boolean before() {
        System.out.println("before was called!");
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