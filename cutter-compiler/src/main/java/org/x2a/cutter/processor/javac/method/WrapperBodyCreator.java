package org.x2a.cutter.processor.javac.method;


import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.processor.javac.TreeFactory;

public abstract class WrapperBodyCreator {
    public static final String GENERATED_VAR_PREFIX = "___generated_var__";
    protected static final String POINT_CUT_VAR_NAME = GENERATED_VAR_PREFIX + "pointCut";

    protected final JCNewClass pointCutClass;
    protected final TreeFactory factory;
    protected final Name wrappedMethodName;

    public WrapperBodyCreator(TreeFactory factory, JCNewClass pointCutClass, Name wrappedMethodName) {
        this.pointCutClass = pointCutClass;
        this.factory = factory;
        this.wrappedMethodName = wrappedMethodName;
    }

    public abstract JCBlock createMethodBody();

    protected JCStatement createPointCutVar() {
        return factory.VariableDeclaration(factory.Modifiers(Flags.FINAL), factory.getName(POINT_CUT_VAR_NAME), pointCutClass.clazz, pointCutClass);
    }

    protected JCIf createConditionalStatement(JCStatement thenStmt, JCStatement elseStmt) {
        JCMethodInvocation beforeInvocation = factory.createMethodInvocation(List.nil(),
                factory.FieldAccess(factory.Ident(POINT_CUT_VAR_NAME), factory.getName("before")), factory.List());
        return factory.If(beforeInvocation, thenStmt, elseStmt);
    }

    protected JCMethodInvocation createWrappedMethodInvocation() {
        return factory.createMethodInvocation(factory.List(), factory.Ident(wrappedMethodName), factory.List()); //TODO: args
    }

}
