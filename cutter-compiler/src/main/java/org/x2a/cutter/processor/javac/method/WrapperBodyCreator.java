package org.x2a.cutter.processor.javac.method;


import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import org.x2a.cutter.processor.javac.TreeFactory;

public abstract class WrapperBodyCreator {
    protected static final String POINT_CUT_VAR_NAME = "pointCut";

    protected final JCNewClass pointCutClass;
    protected final TreeFactory factory;

    public WrapperBodyCreator(TreeFactory factory, JCNewClass pointCutClass) {
        this.pointCutClass = pointCutClass;
        this.factory = factory;
    }

    abstract JCBlock createMethodBody();

    protected JCStatement createPointCutVar() {
        return factory.VariableDeclaration(factory.Modifiers(Flags.FINAL), factory.getName(POINT_CUT_VAR_NAME), pointCutClass.clazz, pointCutClass);
    }

}
