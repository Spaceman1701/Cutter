package org.x2a.cutter.processor.javac.method;

import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCNewClass;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.processor.javac.TreeFactory;

public class ReturningBodyCreator extends WrapperBodyCreator {


    public ReturningBodyCreator(TreeFactory factory, JCNewClass pointCutClass, Name wrappedMethodName, List<JCVariableDecl> params) {
        super(factory, pointCutClass, wrappedMethodName, params);
    }

    @Override
    public JCBlock createMethodBody() {
        return null;
    }
}
