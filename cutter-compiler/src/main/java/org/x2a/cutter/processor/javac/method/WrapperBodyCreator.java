package org.x2a.cutter.processor.javac.method;


import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.Constants;
import org.x2a.cutter.processor.javac.TreeFactory;

public abstract class WrapperBodyCreator {
    protected static final String POINT_CUT_VAR_NAME = Constants.GENERATED_VAR_PREFIX + "pointCut";

    protected final TreeFactory factory;

    private final JCNewClass pointCutClass;
    private final Name wrappedMethodName;
    private final List<JCVariableDecl> params;

    protected WrapperBodyCreator(TreeFactory factory, JCNewClass pointCutClass, Name wrappedMethodName, List<JCVariableDecl> params) {
        this.pointCutClass = pointCutClass;
        this.factory = factory;
        this.wrappedMethodName = wrappedMethodName;
        this.params = params;
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
        return factory.createMethodInvocation(factory.List(), factory.Ident(wrappedMethodName), createMethodCallParams()); //TODO: args
    }

    private List<JCExpression> createMethodCallParams() {
        List<JCExpression> result = factory.List();
        for (int i = 0; i < params.length(); i++) {
            JCTree type = params.get(i).vartype;
            JCExpression getValueExp = currentValueExpression(i);

            JCExpression actualValueExp = factory.TypeCast(type, getValueExp);

            result = result.append(actualValueExp);
        }

        return result;
    }

    protected JCFieldAccess getPointCutField(String name) {
        return factory.FieldAccess(factory.Ident(POINT_CUT_VAR_NAME), factory.getName(name));
    }

    protected JCMethodInvocation getPointCutMethodInvoke(String methodName, List<JCExpression> params) {
        return factory.createMethodInvocation(factory.List(), getPointCutField(methodName), params);
    }

    private JCExpression currentValueExpression(int index) {
        JCFieldAccess getParamField = factory.FieldAccess(factory.Ident(POINT_CUT_VAR_NAME), factory.getName("getParameterValue"));
        return factory.createMethodInvocation(factory.List(), getParamField, List.of(factory.Literal(index)));
    }
}
