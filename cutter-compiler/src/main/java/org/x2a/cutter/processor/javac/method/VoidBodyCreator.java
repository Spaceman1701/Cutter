package org.x2a.cutter.processor.javac.method;

import com.sun.tools.javac.tree.JCTree;
import org.x2a.cutter.processor.javac.TreeFactory;

public class VoidBodyCreator extends WrapperBodyCreator {

    public VoidBodyCreator(TreeFactory factory, JCTree.JCNewClass pointCutClass) {
        super(factory, pointCutClass);
    }

    @Override
    JCTree.JCBlock createMethodBody() {
        return null;
    }
}
