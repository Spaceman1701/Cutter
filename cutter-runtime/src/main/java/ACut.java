import org.x2a.cutter.cut.PointCut;

public class ACut extends PointCut<Object> {
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
    public Object after(Object[] args) {
        return null;
    }
}