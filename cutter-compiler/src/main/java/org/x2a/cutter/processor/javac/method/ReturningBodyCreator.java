package org.x2a.cutter.processor.javac.method;

import com.sun.imageio.plugins.jpeg.JPEG;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.processor.javac.TreeFactory;

public class ReturningBodyCreator extends WrapperBodyCreator {

    private final JCTree restype;

    public ReturningBodyCreator(TreeFactory factory, JCNewClass pointCutClass, Name wrappedMethodName, List<JCVariableDecl> params, JCTree restype) {
        super(factory, pointCutClass, wrappedMethodName, params);
        this.restype = restype;
    }

    @Override
    public JCBlock createMethodBody() {
        List<JCTree.JCStatement> statements = factory.List();

        statements = statements.append(createPointCutVar());

        statements = statements.append(createConditionalStatement(createIfStmt(), createElseStmt()));

        return factory.Block(0, statements);
    }

    private JCStatement createIfStmt() {
        JCMethodInvocation wrappedMethodInvocation = createWrappedMethodInvocation();
        JCMethodInvocation pointCutAfterMethodInvocation = getPointCutMethodInvoke("after", List.of(wrappedMethodInvocation));
        return factory.Return(factory.TypeCast(restype, pointCutAfterMethodInvocation));
    }

    private JCStatement createElseStmt() {
        return factory.Return(factory.TypeCast(restype, getPointCutMethodInvoke("onSkip", factory.List())));
    }
}
