import org.x2a.cutter.cut.Action;
import org.x2a.cutter.cut.PointCut;

public class ACut extends PointCut<Object> {
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