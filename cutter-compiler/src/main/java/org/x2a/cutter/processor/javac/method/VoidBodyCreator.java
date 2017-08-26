package org.x2a.cutter.processor.javac.method;

import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.processor.javac.TreeFactory;

public class VoidBodyCreator extends WrapperBodyCreator {

    public VoidBodyCreator(TreeFactory factory, JCNewClass pointCutClass, Name wrappedMethodName, List<JCVariableDecl> params) {
        super(factory, pointCutClass, wrappedMethodName, params);
    }

    @Override
    public JCBlock createMethodBody() {
        List<JCStatement> statements = factory.List();

        statements = statements.append(createPointCutVar());

        JCMethodInvocation methodInvocation = createWrappedMethodInvocation();

        statements = statements.append(createConditionalStatement(factory.Exec(methodInvocation), factory.Skip()));

        return factory.Block(0, statements);
    }
}
