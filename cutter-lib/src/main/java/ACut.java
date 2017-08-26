import org.x2a.cutter.cut.JoinPoint;
import org.x2a.cutter.cut.Parameter;
import org.x2a.cutter.cut.PointCut;
import org.x2a.cutter.pointcut.AbstractPointCut;

import java.util.Arrays;

public class ACut extends AbstractPointCut<Object> {

    public ACut(JoinPoint joinPoint, Parameter[] parameters) {
        super(joinPoint, parameters);
        System.out.println("ACut was created!");
    }

    @Override
    public boolean before() {
        System.out.println(Arrays.toString(getParameterTypes()));
        System.out.println("before was called at jp: " + joinPoint.toString());
        return true;
    }

    @Override
    public Object after(Object returnValue) {
        return returnValue;
    }

    @Override
    public Object onSkip() {
        System.out.println("On skip was called");
        return null;
    }
}