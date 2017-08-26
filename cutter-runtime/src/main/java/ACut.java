import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;

public class ACut extends PointCut<Object> {

    public ACut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
        System.out.println("ACut was created!");
    }

    @Override
    public boolean before() {
        System.out.println("before was called at jp: " + joinPoint.toString());
        return true;
    }

    @Override
    public Object after(Object returnValue) {
        System.out.println("After was called!");
        return null;
    }

    @Override
    public Object onSkip() {
        System.out.println("On skip was called");
        return null;
    }
}