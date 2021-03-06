package org.x2a.cutter.processor.javac.method;

import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
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

        statements = statements.append(createConditionalStatement(createIfBlock(), createElseStatment()));

        return factory.Block(0, statements);
    }

    private JCBlock createIfBlock() {
        JCStatement wrappedMethodInvokeStmt = factory.Exec(createWrappedMethodInvocation());
        JCStatement pointCutAfterInvokeStmt = factory.Exec(getPointCutMethodInvoke("after", List.of(factory.Null())));

        return factory.Block(0, List.of(wrappedMethodInvokeStmt, pointCutAfterInvokeStmt));
    }

    private JCStatement createElseStatment() {
        return factory.Exec(getPointCutMethodInvoke("onSkip", factory.List()));
    }
}
