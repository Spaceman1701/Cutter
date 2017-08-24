package org.x2a.cutter.processor.javac.method;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.*;
import com.sun.tools.javac.util.Name;
import org.x2a.cutter.processor.javac.TreeFactory;

public class ReturningBodyCreator extends WrapperBodyCreator {


    public ReturningBodyCreator(TreeFactory factory, JCNewClass pointCutClass, Name wrappedMethodName) {
        super(factory, pointCutClass, wrappedMethodName);
    }

    @Override
    public JCBlock createMethodBody() {
        return null;
    }
}
